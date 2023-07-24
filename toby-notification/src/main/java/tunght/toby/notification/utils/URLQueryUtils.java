package tunght.toby.notification.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class URLQueryUtils {
    public static Map<String, String> getParams(URI uri) {
        Map<String, String> result = new HashMap<>();
        if (uri == null) {
            return result;
        }
        String query = uri.getQuery();
        if (query == null) {
            return result;
        }
        String[] infos = query.split("&");
        for (String s : infos) {
            if (s != null && !"".equals(s) && s.contains("=")) {
                String[] info = s.split("=");
                if (info.length >= 2) {
                    String key = info[0];
                    String val = info[1];
                    result.put(key, val);
                }
            }
        }
        return result;
    }
}
