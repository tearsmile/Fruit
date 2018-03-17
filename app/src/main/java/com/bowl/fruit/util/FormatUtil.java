package com.bowl.fruit.util;

import java.text.DecimalFormat;

/**
 * Created by CJ on 2018/3/16.
 */

public class FormatUtil {
    public static String formatDouble(double digital){
        DecimalFormat df   = new DecimalFormat("######0.00");
        return df.format(digital);
    }
}
