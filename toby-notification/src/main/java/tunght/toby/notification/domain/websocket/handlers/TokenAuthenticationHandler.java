package tunght.toby.notification.domain.websocket.handlers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import tunght.toby.notification.domain.websocket.manager.UserManager;
import tunght.toby.notification.domain.websocket.network.IClientConnection;

import java.util.UUID;

@Service
public class TokenAuthenticationHandler extends BaseClientRequestHandler {

    @Override
    protected JSONObject process(IClientConnection connection, JSONObject params) {

        String accessToken = params.getString("access_token");

        String sessionId = params.optString("sessionId", "");
        if (sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
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

        String keySid = null;
        String userId = null;
        String alg = null;

        alg = jwt2.getAlgorithm(); //HS256 | RS256
        Claim jwtIdClaim = jwt2.getClaim("jti");
        Claim keySidClaim = jwt2.getClaim("iss");
        Claim expClaim = jwt2.getClaim("exp");
//        Claim userIdClaim = jwt2.getClaim("user_id");
        Claim userIdClaim = jwt2.getClaim("sub");

        if (userIdClaim.isNull()) {
            logger.info("jwtIdClaim == null || keySidClaim == null || expClaim == null || userIdClaim == " + userIdClaim);
            JSONObject resp = new JSONObject();
            resp.put("rc", -1);
            resp.put("rd", "Access token invalid");
            return resp;
        }

        userId = userIdClaim.asString();

        UserManager.manageUserOnline(userId, sessionId, connection);

        JSONObject resp = new JSONObject();
        resp.put("rc", 0);
        resp.put("rd", "Authentication success");
        return resp;
    }

}
