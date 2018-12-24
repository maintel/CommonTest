package com.maintel.customview.utils;

import android.util.Log;

/**
 * <p>name:</p>
 * <p>describe:</p>
 *
 * @author Maintel
 * @time 2018/6/22 1:13
 */

public class LogUtils {

    public static void d(int s) {
        d(s + "");
    }

    public static void d(String s) {
        Log.d("MAINTEL>>>>", s);
    }

}
