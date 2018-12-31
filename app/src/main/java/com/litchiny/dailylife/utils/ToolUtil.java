package com.litchiny.dailylife.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ll on 2018/8/12.
 */

public class ToolUtil {
    public static void showToast(Context context, String desc) {
        Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int descId) {
        Toast.makeText(context, descId, Toast.LENGTH_SHORT).show();
    }

}
