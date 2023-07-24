package tunght.toby.notification.domain.websocket.manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tunght.toby.notification.domain.websocket.handlers.BaseClientRequestHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClientRequestHandlerManager implements CommandLineRunner {

    private final Map<Integer, BaseClientRequestHandler> HANDLERS = new ConcurrentHashMap<>();

    public ClientRequestHandlerManager() {

    }

    public BaseClientRequestHandler getHandler(int cmd) {
        return HANDLERS.get(cmd);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
