package tunght.toby.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import tunght.toby.common.security.AuthUserDetails;

@Configuration
public class RedisConfig {

    @Autowired
    RedisConnectionFactory factory;

    // just an example, I'm not using it in this project
    @Bean
    public RedisTemplate<String, AuthUserDetails> redisTemplateTokenUser() {
        var template = new RedisTemplate<String, AuthUserDetails>();
        template.setConnectionFactory(factory);
        return template;
    }
}
