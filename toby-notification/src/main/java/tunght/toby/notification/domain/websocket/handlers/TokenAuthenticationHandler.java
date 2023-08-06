package tunght.toby.notification.domain.websocket.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tunght.toby.common.security.AuthUserDetails;
import tunght.toby.common.utils.JsonConverter;
import tunght.toby.notification.domain.websocket.manager.UserManager;
import tunght.toby.notification.domain.websocket.network.IClientConnection;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationHandler extends BaseClientRequestHandler {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected JSONObject process(IClientConnection connection, JSONObject params) {

        String accessToken = params.getString("access_token");

        String sessionId = params.optString("sessionId", "");
        if (sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }

        var isValidToken = redisTemplate.hasKey(accessToken);
        if (Boolean.FALSE.equals(isValidToken)) {
            JSONObject resp = new JSONObject();
            resp.put("rc", -1);
            resp.put("rd", "Unauthorized");
            return resp;
        }

        var jsonStr = redisTemplate.opsForValue().get(accessToken);
        String userId = JsonConverter.deserializeObject(jsonStr, AuthUserDetails.class)
                .getId().toString();

        UserManager.manageUserOnline(userId, sessionId, connection);

        JSONObject resp = new JSONObject();
        resp.put("rc", 0);
        resp.put("rd", "Authentication success");

        return resp;
    }

}
