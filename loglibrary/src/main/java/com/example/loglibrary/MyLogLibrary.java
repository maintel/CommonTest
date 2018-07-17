package com.example.loglibrary;

import android.content.Context;
import android.content.Intent;

/**
 * @author jieyu.chen
 * @date 2018/7/17
 */
public class MyLogLibrary {

    public static void init(Context context) {
        context.startService(new Intent(context, LogService.class));
    }

}
