package tunght.toby.notification.domain.websocket.network;

import tunght.toby.notification.entity.Variable;

import java.util.List;

public interface IClientConnection {
    long getId();

    int getType();

    void send(IPacket packet);

    void disconnect(int reason, String message);

    void setUser(IUser user);

    IUser getUser();

    String getSessionId();

    void setSessionId(String sessionId);

    void setVariables(List<Variable> variables);

    void setVariable(Variable variable);

    Variable getVariable(String key);
}
