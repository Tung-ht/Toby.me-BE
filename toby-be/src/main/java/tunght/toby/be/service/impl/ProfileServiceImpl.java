package tunght.toby.be.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.be.dto.NotificationDto;
import tunght.toby.be.dto.ProfileDto;
import tunght.toby.be.entity.FollowEntity;
import tunght.toby.be.repository.FollowRepository;
import tunght.toby.be.repository.UserRepository;
import tunght.toby.be.service.ProfileService;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.ENotifications;
import tunght.toby.common.enums.EStatus;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.Error;
import tunght.toby.common.security.AuthUserDetails;
import tunght.toby.common.utils.JsonConverter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Value("${spring.kafka.follow-noti-topic}")
    private String followTopic;
    private final KafkaTemplate<String, Object> notiKafkaTemplate;

    @Override
    public ProfileDto getProfile(String name, AuthUserDetails authUserDetails) {
        UserEntity user = userRepository.findAllByUsernameAndStatus(name, EStatus.ACTIVE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        boolean following = false;
        if (authUserDetails != null) {
            following = followRepository.findByFolloweeIdAndFollowerId(user.getId(), authUserDetails.getId()).isPresent();
        }
        return convertToProfile(user, following);
    }

    @Transactional
    @Override
    public ProfileDto followUser(String name, AuthUserDetails authUserDetails) {
        UserEntity followee = userRepository.findAllByUsernameAndStatus(name, EStatus.ACTIVE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        UserEntity follower = UserEntity.builder()
                .id(authUserDetails.getId())
                .username(authUserDetails.getUsername())
                .build(); // myself

        followRepository.findByFolloweeIdAndFollowerId(followee.getId(), follower.getId())
                .ifPresent(follow -> {throw new AppException(Error.ALREADY_FOLLOWED_USER);});

        FollowEntity follow =  FollowEntity.builder().followee(followee).follower(follower).build();
        followRepository.save(follow);

        String username = getUsernameById(authUserDetails.getId());

        NotificationDto notificationDto = NotificationDto.builder()
                .type(ENotifications.FOLLOW)
                .fromUserId(follower.getId().toString())
                .toUserId(followee.getId().toString())
                .message(ENotifications.getNotificationMessage(ENotifications.FOLLOW, username))
                .isRead(false)
                .build();
        notiKafkaTemplate.send(followTopic, JsonConverter.serializeObject(notificationDto));

        return convertToProfile(followee, true);
    }

    @Transactional
    @Override
    public ProfileDto unfollowUser(String name, AuthUserDetails authUserDetails) {
        UserEntity followee = userRepository.findAllByUsernameAndStatus(name, EStatus.ACTIVE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        UserEntity follower = UserEntity.builder().id(authUserDetails.getId()).build(); // myself

        FollowEntity follow = followRepository.findByFolloweeIdAndFollowerId(followee.getId(), follower.getId())
                .orElseThrow(() -> new AppException(Error.FOLLOW_NOT_FOUND));
        followRepository.delete(follow);

        return convertToProfile(followee, false);
    }

    @Override
    public ProfileDto getProfileByUserId(Long userId, AuthUserDetails authUserDetails) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        boolean following = false;
        if (authUserDetails != null) {
            following = followRepository.findByFolloweeIdAndFollowerId(user.getId(), authUserDetails.getId()).isPresent();
        }
        return convertToProfile(user, following);
    }

    @Override
    public String getUsernameById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND))
                .getUsername();
    }

    private ProfileDto convertToProfile(UserEntity user, Boolean following) {
        return ProfileDto.builder()
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .following(following)
                .build();
    }
}
