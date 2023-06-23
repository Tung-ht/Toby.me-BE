package tunght.toby.common.utils;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson = new Gson();

    public static String serializeObject(Object object) {
        return gson.toJson(object);
    }

    public static <T> T deserializeObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
