package tunght.toby.auth.service;

import tunght.toby.auth.consts.EUserAction;
import tunght.toby.auth.dto.UserDto;
import tunght.toby.common.security.AuthUserDetails;

public interface UserService {
    UserDto.RegistrationResponse registration(final UserDto.Registration registration);

    String login(final UserDto.Login login);

    UserDto currentUser(final AuthUserDetails authUserDetails);

    UserDto update(final UserDto.Update update, final AuthUserDetails authUserDetails);

    String requestVerify(EUserAction action, UserDto.RequestOTP requestOTP);

    void sendOTP(EUserAction action, String email);
}
