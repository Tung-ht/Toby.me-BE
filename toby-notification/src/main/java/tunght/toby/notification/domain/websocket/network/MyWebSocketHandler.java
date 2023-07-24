package tunght.toby.notification.domain.websocket.network;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tunght.toby.notification.config.CmdDefs;
import tunght.toby.notification.domain.websocket.manager.SocketClientManger;
import tunght.toby.notification.utils.URLQueryUtils;

import java.net.URI;
import java.util.Map;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SocketClientManger socketClientManger;

    public MyWebSocketHandler(SocketClientManger socketClientManger) {
        this.socketClientManger = socketClientManger;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        IClientConnection connection = (IClientConnection) session.getAttributes().get("connection");
        if (connection == null) {
            return;
        }
        logger.info("connect close: " + connection.getUser().getUserId());
        socketClientManger.onDisconnect(connection, status.getCode(), status.getReason());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        IClientConnection connection = new WebsocketClientConnection(session);
        session.getAttributes().put("connection", connection);

        URI uri = session.getUri();
        String accessToken = "";

        if (uri != null) {
            Map<String, String> maps = URLQueryUtils.getParams(uri);
            if (maps.containsKey("access_token")) {
                accessToken = maps.get("access_token");
            }
        }

        JSONObject params = new JSONObject();
        params.put("access_token", accessToken);
        params.put("request_id", -1);

        // put token auth command
        IPacket packet = new UserPacket(CmdDefs.WEBSOCKET_TOKEN_AUTH_CMD, params);
        socketClientManger.onMessage(connection, packet);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        IClientConnection connection = (IClientConnection) session.getAttributes().get("connection");
        if (connection == null) {
            return;
        }
        try {
            String payload = textMessage.getPayload();
            if (payload.isEmpty()) {
                logger.info("payload invalid 1");
                return;
            }
            JSONObject data = new JSONObject(payload);
            if (!data.has("cmd") || !data.has("params")) {
                logger.info("payload invalid 2");
                return;
            }
            IPacket packet = new UserPacket(data.getInt("cmd"), data.getJSONObject("params"));
            socketClientManger.onMessage(connection, packet);
        } catch (Throwable ex) {
            logger.error("handleTextMessage Ex: ", ex);
        }
    }

}
