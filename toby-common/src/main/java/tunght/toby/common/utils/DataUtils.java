package tunght.toby.common.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class DataUtils {
    /*
    sample output: 002428, 445307, 409185
     */
    public static String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
