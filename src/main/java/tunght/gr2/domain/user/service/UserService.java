package tunght.gr2.domain.user.service;

import tunght.gr2.domain.user.dto.UserDto;
import tunght.gr2.security.AuthUserDetails;

public interface UserService {
    UserDto registration(final UserDto.Registration registration);

    UserDto login(final UserDto.Login login);

    UserDto currentUser(final AuthUserDetails authUserDetails);

    UserDto update(final UserDto.Update update, final AuthUserDetails authUserDetails);
}
