package com.bowl.fruit.util;

import java.text.SimpleDateFormat;

/**
 * Created by CJ on 2018/2/21.
 */

public class TimeUtil {

    private static final String FORMAT_TIME = "yyyy-MM-dd hh:mm:ss";

    public static String format(long timestamp){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(timestamp);
    }
}
