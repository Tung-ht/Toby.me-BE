package tunght.toby.notification.domain.websocket.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class User implements IUser {

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
    private final String userId;
    private final List<IClientConnection> connections = new CopyOnWriteArrayList<>();
    private final Map<String, IUserClient> userClients = new ConcurrentHashMap<>();

    public User(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void addConnection(IClientConnection connection) {
        for (IClientConnection conn : connections) {
            if (conn.getId() == connection.getId()) {
                LOGGER.info("user: " + userId + " already has this connection: " + conn.getId());
                return;
            }
        }
        connections.add(connection);
        // 1 user co the co nhieu connection, cho phep nhieu ket noi
        IUserClient userClient = userClients.get(connection.getSessionId());
        if (userClient == null) {

        }

    }

    @Override
    public void removeConnection(IClientConnection connection) {
        if (!connections.isEmpty()) {
            connections.removeIf(s -> s.getId() == connection.getId());
        }
    }

    @Override
    public List<IClientConnection> getConnections() {
        return connections;
    }

    @Override
    public void addUserClient(IUserClient userClient) {

    }

    @Override
    public void removeUserClient(IUserClient userClient) {

    }

    @Override
    public IUserClient getUserClient(String sessionId) {
        return null;
    }

    @Override
    public void sendMessageTo(IPacket packet) {
        if (connections.isEmpty()) {
            return;
        }
        for (IClientConnection connection : connections) {
            connection.send(packet);
        }
    }

}
