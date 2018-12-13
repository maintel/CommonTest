package com.example.loglibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * @author jieyu.chen
 * @date 2018/7/17
 */
public class MyLogLibrary {

    public static void init(Activity context) {
        context.startService(new Intent(context, LogService.class));
    }

}
