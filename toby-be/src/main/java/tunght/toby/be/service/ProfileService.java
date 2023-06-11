package tunght.toby.be.service;

import tunght.toby.be.dto.ProfileDto;
import tunght.toby.common.security.AuthUserDetails;

public interface ProfileService {
    ProfileDto getProfile(final String username, final AuthUserDetails authUserDetails);

    ProfileDto followUser(final String name, final AuthUserDetails authUserDetails);

    ProfileDto unfollowUser(final String name, final AuthUserDetails authUserDetails);

    ProfileDto getProfileByUserId(Long userId, AuthUserDetails authUserDetails);
}
