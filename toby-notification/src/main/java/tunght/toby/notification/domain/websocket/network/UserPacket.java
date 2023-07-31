package tunght.toby.notification.domain.websocket.network;

import org.json.JSONObject;

public class UserPacket implements IPacket {

    private final int cmd;
    private JSONObject params;

    public UserPacket(int cmd) {
        this.cmd = cmd;
    }

    public UserPacket(int cmd, JSONObject params) {
        this.cmd = cmd;
        this.params = params;
    }

    @Override
    public int getCmd() {
        return cmd;
    }

    @Override
    public JSONObject getParams() {
        return params;
    }

    @Override
    public void addField(String field, Object value) {
        if (params == null) {
            params = new JSONObject();
        }
        params.put(field, value);
    }
}
