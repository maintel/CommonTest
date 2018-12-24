package com.maintel.customview;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author jieyu.chen
 * @date 2018/10/25
 */
public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    public static MyApplication getInstance() {
        return INSTANCE;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initARouter();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
