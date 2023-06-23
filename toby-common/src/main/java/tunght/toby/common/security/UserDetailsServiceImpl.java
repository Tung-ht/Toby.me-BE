package tunght.toby.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RedisTemplate<String, AuthUserDetails> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        return redisTemplate.opsForValue().get(token);
    }
}
