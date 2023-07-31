package tunght.toby.notification.domain.websocket.network;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tunght.toby.notification.config.Cache;
import tunght.toby.notification.entity.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsocketClientConnection implements IClientConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger("WebsocketClientConnection");
    private final Map<String, Variable> variables = new HashMap<>();
    private final long id;
    private final WebSocketSession session;
    private IUser user;
    private String sessionId;

    public WebsocketClientConnection(WebSocketSession session) {
        this.session = session;
        this.id = Cache.GENERATOR.incrementAndGet();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getType() {
        return SocketTypeDefs.WEBSOCKET;
    }

    @Override
    public void send(IPacket packet) {
        try {
            JSONObject data = new JSONObject();
            data.put("cmd", packet.getCmd());
            data.put("params", packet.getParams());
            if (session.isOpen()) {
                synchronized (session) {
                    session.sendMessage(new TextMessage(data.toString()));
                }
            }
        } catch (Throwable ex) {
            LOGGER.error("WebsocketClientConnection.send() Ex: ", ex);
        }
    }

    @Override
    public void disconnect(int reason, String message) {
        try {
            if (session != null && session.isOpen()) {
                session.close(new CloseStatus(reason, message));
            }
        } catch (Throwable ex) {
            LOGGER.error("WebsocketClientConnection.disconnect() Ex: ", ex);
        }
    }

    @Override
    public void setUser(IUser user) {
        this.user = user;
    }

    @Override
    public IUser getUser() {
        return this.user;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void setVariables(List<Variable> variables) {
        for (Variable v : variables) {
            this.variables.put(v.getKey(), v);
        }
    }

    @Override
    public void setVariable(Variable variable) {
        variables.put(variable.getKey(), variable);
    }

    @Override
    public Variable getVariable(String key) {
        return variables.get(key);
    }

}
