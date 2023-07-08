package tunght.toby.be.service;

import tunght.toby.be.consts.EUserAction;
import tunght.toby.be.dto.UserDto;
import tunght.toby.common.security.AuthUserDetails;

public interface UserService {
    UserDto.RegistrationResponse registration(final UserDto.Registration registration);

    String login(final UserDto.Login login);

    UserDto currentUser(final AuthUserDetails authUserDetails);

    UserDto update(final UserDto.Update update, final AuthUserDetails authUserDetails);

    void requestVerify(EUserAction action, UserDto.RequestOTP requestOTP);

    void sendOTP(EUserAction action, String email);
}
