package tunght.toby.notification.domain.websocket.manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tunght.toby.notification.config.CmdDefs;
import tunght.toby.notification.domain.websocket.handlers.BaseClientRequestHandler;
import tunght.toby.notification.domain.websocket.handlers.TokenAuthenticationHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientRequestHandlerManager implements CommandLineRunner {

    private final Map<Integer, BaseClientRequestHandler> HANDLERS = new ConcurrentHashMap<>();

    public ClientRequestHandlerManager(TokenAuthenticationHandler tokenAuthenticationHandler) {
        HANDLERS.put(CmdDefs.WEBSOCKET_TOKEN_AUTH_CMD, tokenAuthenticationHandler);
    }

    public BaseClientRequestHandler getHandler(int cmd) {
        return HANDLERS.get(cmd);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
