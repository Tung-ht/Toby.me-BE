package tunght.gr2.service;

import tunght.gr2.dto.ProfileDto;
import tunght.gr2.security.AuthUserDetails;

public interface ProfileService {
    ProfileDto getProfile(final String username, final AuthUserDetails authUserDetails);

    ProfileDto followUser(final String name, final AuthUserDetails authUserDetails);

    ProfileDto unfollowUser(final String name, final AuthUserDetails authUserDetails);

    ProfileDto getProfileByUserId(Long userId, AuthUserDetails authUserDetails);
}
