package tunght.toby.notification.domain.websocket.network;

public interface IUserClient {
    void addConnection(IClientConnection connection);

    void removeConnection(IClientConnection connection);
}
