package tunght.toby.be.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.be.consts.EUserAction;
import tunght.toby.be.dto.UserDto;
import tunght.toby.be.repository.RoleRepository;
import tunght.toby.be.repository.UserRepository;
import tunght.toby.be.service.UserService;
import tunght.toby.be.utils.MailSender;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.ERole;
import tunght.toby.common.enums.EStatus;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.Error;
import tunght.toby.common.security.AuthUserDetails;
import tunght.toby.common.security.JwtUtils;
import tunght.toby.common.utils.JsonConverter;

import java.time.Duration;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final MailSender mailSender;

    @Override
    public UserDto.RegistrationResponse registration(UserDto.Registration registration) {
        userRepository.findAllByEmailAndStatus(registration.getEmail(), EStatus.ACTIVE).stream().findAny().ifPresent(entity -> {throw new AppException(Error.DUPLICATED_USER);});
        userRepository.findAllByUsernameAndStatus(registration.getUsername(), EStatus.ACTIVE).stream().findAny().ifPresent(entity -> {throw new AppException(Error.DUPLICATED_USER);});
        UserEntity userEntity = UserEntity.builder()
                .username(registration.getUsername())
                .email(registration.getEmail())
                .password(passwordEncoder.encode(registration.getPassword()))
                .bio("")
                .status(EStatus.INACTIVE)
                .roles(new HashSet<>())
                .build();
        final var ROLE_USER = roleRepository.findByRole(ERole.ROLE_USER).orElseThrow(() -> new AppException(Error.ROLE_NOT_FOUND));
        userEntity.getRoles().add(ROLE_USER);
        userEntity = userRepository.save(userEntity);
        mailSender.sendMail(userEntity.getEmail(), EUserAction.VERIFY_EMAIL);
        return UserDto.RegistrationResponse.builder().email(userEntity.getEmail()).build();
    }

    @Transactional(readOnly = true)
    @Override
    public String login(UserDto.Login login) {
        UserEntity userEntity = userRepository.findByEmailAndStatus(login.getEmail(), EStatus.ACTIVE).filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword())).orElseThrow(() -> new AppException(Error.LOGIN_INFO_INVALID));
        String jwt = jwtUtils.encode(userEntity.getEmail());
        var userDetail = AuthUserDetails.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .authorities(userEntity.getRoles())
                .build();
        // when an user logged in, (jwt-userInfo)-(string-jsonString) is cached into redis
        var jsonStr = JsonConverter.serializeObject(userDetail);
        redisTemplate.opsForValue()
                .set(jwt, jsonStr, Duration.ofSeconds(jwtUtils.getValidSeconds()));
        return jwt;
    }

    private UserDto convertEntityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .username(userEntity.getUsername())
                .bio(userEntity.getBio())
                .email(userEntity.getEmail())
                .image(userEntity.getImage())
                .roles(userEntity.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList()))
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto currentUser(AuthUserDetails authUserDetails) {
        UserEntity userEntity = userRepository.findById(authUserDetails.getId()).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        return convertEntityToDto(userEntity);
    }

    @Override
    public UserDto update(UserDto.Update update, AuthUserDetails authUserDetails) {
        UserEntity userEntity = userRepository.findById(authUserDetails.getId()).orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));

        if (update.getUsername() != null) {
            userRepository.findByUsername(update.getUsername())
                    .filter(found -> !found.getId().equals(userEntity.getId()))
                    .ifPresent(found -> {throw new AppException(Error.DUPLICATED_USER);});
            userEntity.setUsername(update.getUsername());
        }

        if (update.getBio() != null) {
            userEntity.setBio(update.getBio());
        }

        if (update.getImage() != null) {
            userEntity.setImage(update.getImage());
        }

        userRepository.save(userEntity);
        return convertEntityToDto(userEntity);
    }

    @Override
    public void registrationVerify(UserDto.RegistrationOTP registrationOTP) {
        var user = userRepository.findLatestCreatedAccountByMail(registrationOTP.getEmail())
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        var otp = user.getOtp();
        if (otp == null || !otp.equals(registrationOTP.getOtp())) {
            throw new AppException(Error.OTP_INVALID);
        } else {
            user.setStatus(EStatus.ACTIVE);
            userRepository.save(user);
        }
    }

    @Override
    public void resendOTP(EUserAction action, String email) {
        var users = userRepository.findByEmail(email);
        if (users.isEmpty()) {
            throw new AppException(Error.USER_NOT_FOUND);
        }
        mailSender.sendMail(email, action);
    }
}
