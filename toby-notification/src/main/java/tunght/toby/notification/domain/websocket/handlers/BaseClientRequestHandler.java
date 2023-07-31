package tunght.toby.notification.domain.websocket.handlers;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tunght.toby.notification.domain.websocket.network.IClientConnection;
import tunght.toby.notification.domain.websocket.network.IPacket;
import tunght.toby.notification.domain.websocket.network.UserPacket;

@Service
public abstract class BaseClientRequestHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public void handleClientRequest(IClientConnection connection, IPacket packet) {

        JSONObject params = packet.getParams();

        if (params == null) {
            params = new JSONObject();
        }

        long requestId = params.optLong("request_id", -1);
        params.remove("request_id");

        JSONObject resp = process(connection, params);

        if (resp != null) {
            IPacket data = new UserPacket(packet.getCmd(), resp);
            data.addField("request_id", requestId);
            connection.send(data);
        }

    }

    protected abstract JSONObject process(IClientConnection connection, JSONObject params);

    protected JSONObject buildResponse(int rc, String rd) {
        JSONObject response = new JSONObject();
        response.put("rc", rc);
        response.put("rd", rd);
        return response;
    }

}
