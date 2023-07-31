package tunght.toby.notification.domain.websocket.network;

import org.json.JSONObject;

public interface IPacket {
    int getCmd();

    JSONObject getParams();

    void addField(String field, Object value);
}
