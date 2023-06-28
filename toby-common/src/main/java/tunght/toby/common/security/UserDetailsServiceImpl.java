package tunght.toby.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tunght.toby.common.utils.JsonConverter;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        // when an user logged in, (jwt-userInfo)-(string-jsonString) is cached into redis
        var jsonStr = redisTemplate.opsForValue().get(token);
        return JsonConverter.deserializeObject(jsonStr, AuthUserDetails.class);
    }
}
