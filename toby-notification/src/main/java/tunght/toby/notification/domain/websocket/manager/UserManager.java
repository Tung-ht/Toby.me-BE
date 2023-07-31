package tunght.toby.notification.domain.websocket.manager;

import tunght.toby.notification.domain.websocket.network.IClientConnection;
import tunght.toby.notification.domain.websocket.network.IUser;
import tunght.toby.notification.domain.websocket.network.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private static final Map<String, IUser> USERS = new ConcurrentHashMap<>();

    public static IUser getUser(String userId) {
        return USERS.get(userId);
    }

    public static void addUser(IUser user) {
        USERS.put(user.getUserId(), user);
    }

    public static void removeUser(String userId) {
        USERS.remove(userId);
    }

    public static Collection<IUser> getAllUser() {
        return USERS.values();
    }

    public static void manageUserOnline(String userId, String sessionId, IClientConnection connection) {
        IUser user = getUser(userId);
        if (user == null) {
            user = new User(userId);
            addUser(user);
        }

        connection.setUser(user);
        // moi ket noi se co  1 session id rieng
        connection.setSessionId(sessionId);

        user.addConnection(connection);
    }
}
