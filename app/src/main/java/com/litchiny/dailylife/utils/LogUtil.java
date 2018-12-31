package com.litchiny.dailylife.utils;

import android.util.Log;

/**
 * Created by ll on 2018/8/12.
 */

public class LogUtil {

    public static void i(String tag, String performClick, String s) {
        Log.i(tag, performClick + "---" + s);
    }

    public static void i(String tag, String s) {
        Log.e(tag,  s);
    }
}
