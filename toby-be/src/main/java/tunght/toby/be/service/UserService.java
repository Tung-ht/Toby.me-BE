package tunght.toby.be.service;

import tunght.toby.be.dto.UserDto;
import tunght.toby.common.security.AuthUserDetails;

public interface UserService {
    UserDto registration(final UserDto.Registration registration);

    UserDto login(final UserDto.Login login);

    UserDto currentUser(final AuthUserDetails authUserDetails);

    UserDto update(final UserDto.Update update, final AuthUserDetails authUserDetails);
}
