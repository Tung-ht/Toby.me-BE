package tunght.toby.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.auth.consts.CommonConst;
import tunght.toby.auth.consts.EUserAction;
import tunght.toby.auth.dto.UserDto;
import tunght.toby.auth.repository.RoleRepository;
import tunght.toby.auth.repository.UserRepository;
import tunght.toby.auth.utils.MailSender;
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
        // maybe there are several accounts with the same email, but only 1 account is activated
        userRepository.findAllByEmailAndStatus(registration.getEmail(), EStatus.ACTIVE).stream().findAny().ifPresent(entity -> {throw new AppException(Error.DUPLICATED_USER);});

        // maybe there are several accounts with the same username, but only 1 account is activated
        userRepository.findAllByUsernameAndStatus(registration.getUsername(), EStatus.ACTIVE).stream().findAny().ifPresent(entity -> {throw new AppException(Error.DUPLICATED_USER);});


        UserEntity userEntity = UserEntity.builder()
                .username(registration.getUsername())
                .email(registration.getEmail())
                .password(passwordEncoder.encode(registration.getPassword()))
                .bio("")
                .image(CommonConst.DEFAULT_AVATAR_URL)
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
        UserEntity userEntity = userRepository.findAllByEmailAndStatus(login.getEmail(), EStatus.ACTIVE)
                .stream()
                .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword()))
                .findFirst()
                .orElseThrow(() -> new AppException(Error.LOGIN_INFO_INVALID));
        String jwt = jwtUtils.encode(userEntity.getEmail());
        var userDetail = AuthUserDetails.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .authorities(userEntity.getRoles())
                .build();
        // when a user logged in, (jwt-userInfo)-(string-jsonString) is cached into redis
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
            userRepository.findAllByUsernameAndStatus(update.getUsername(), EStatus.ACTIVE)
                    .stream()
                    .filter(found -> !found.getId().equals(userEntity.getId()))
                    .findFirst()
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
    public String requestVerify(EUserAction action, UserDto.RequestOTP requestOTP) {
        // maybe there are several accounts with the same email, but only the latest account will be activated
        var user = userRepository.findLatestCreatedAccountByMail(requestOTP.getEmail())
                .orElseThrow(() -> new AppException(Error.USER_NOT_FOUND));
        var otp = user.getOtp();
        // if otp is null -> exception is caught -> Error 422
        if (!otp.equals(requestOTP.getOtp())) {
            throw new AppException(Error.OTP_INVALID);
        } else {
            if (action.equals(EUserAction.VERIFY_EMAIL)) {
                user.setStatus(EStatus.ACTIVE);
                userRepository.save(user);
                return CommonConst.REGISTRATION_SUCCESS_MSG;
            }
            // if RESET_PASSWORD -> return 200
            return CommonConst.RESET_PASSWORD_SUCCESS_MSG;
        }
    }

    @Override
    public void sendOTP(EUserAction action, String email) {
        var users = userRepository.findAllByEmail(email);
        if (users.isEmpty()) {
            throw new AppException(Error.USER_NOT_FOUND);
        }
        mailSender.sendMail(email, action);
    }
}
