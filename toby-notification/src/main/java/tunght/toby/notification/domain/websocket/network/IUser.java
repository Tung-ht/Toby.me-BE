package tunght.toby.notification.domain.websocket.network;

import java.util.List;

public interface IUser {

    String getUserId();

    void addConnection(IClientConnection connection);

    void removeConnection(IClientConnection connection);

    List<IClientConnection> getConnections();

    void addUserClient(IUserClient userClient);

    void removeUserClient(IUserClient userClient);

    IUserClient getUserClient(String sessionId);

    void sendMessage(IPacket packet);

}
