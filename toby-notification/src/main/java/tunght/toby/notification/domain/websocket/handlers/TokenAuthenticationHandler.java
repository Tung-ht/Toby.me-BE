package tunght.toby.notification.domain.websocket.handlers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tunght.toby.common.security.AuthUserDetails;
import tunght.toby.common.utils.JsonConverter;
import tunght.toby.notification.domain.websocket.manager.UserManager;
import tunght.toby.notification.domain.websocket.network.IClientConnection;

import java.util.UUID;

@Service
@RequiredArgsConstructor
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

        DecodedJWT jwt2 = null;
        try {
            jwt2 = JWT.decode(accessToken);
        } catch (JWTDecodeException ex) {
            JSONObject resp = new JSONObject();
            resp.put("rc", -1);
            resp.put("rd", "Token invalid");
            return resp;
        }

//        String keySid = null;
//        String alg = null;
        String userId = null;

//        alg = jwt2.getAlgorithm(); //HS256 | RS256
//        Claim jwtIdClaim = jwt2.getClaim("jti");
//        Claim keySidClaim = jwt2.getClaim("iss");
//        Claim expClaim = jwt2.getClaim("exp");
//        Claim userIdClaim = jwt2.getClaim("user_id");
        Claim emailClaim = jwt2.getClaim("sub");

        if (emailClaim.isNull()) {
            logger.info("---> websocket connect ---> verify JWT: userIdClaim == " + emailClaim);
            JSONObject resp = new JSONObject();
            resp.put("rc", -1);
            resp.put("rd", "Token invalid");
            return resp;
        }

        userId = emailClaim.asString();

        UserManager.manageUserOnline(userId, sessionId, connection);

        JSONObject resp = new JSONObject();
        resp.put("rc", 0);
        resp.put("rd", "Authentication success");
        return resp;
    }

}
