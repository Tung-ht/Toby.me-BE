package tunght.toby.be.service;

import tunght.toby.be.consts.EUserAction;
import tunght.toby.be.dto.UserDto;
import tunght.toby.common.security.AuthUserDetails;

public interface UserService {
    UserDto.RegistrationResponse registration(final UserDto.Registration registration);

    String login(final UserDto.Login login);

    UserDto currentUser(final AuthUserDetails authUserDetails);

    UserDto update(final UserDto.Update update, final AuthUserDetails authUserDetails);

    void registrationVerify(UserDto.RegistrationOTP registrationOTP);

    void resendOTP(EUserAction action, String email);
}
