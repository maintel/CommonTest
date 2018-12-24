package com.maintel.customview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.maintel.customview.MyApplication;

/**
 * <p>name:</p>
 * <p>describe:</p>
 *
 * @author Maintel
 * @time 2017/7/28 0:14
 */

public class DeviceUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }


    public static int getDeviceWidth() {
        try {
            WindowManager manager = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            return manager.getDefaultDisplay().getWidth();
        } catch (Exception e) {
            return 0;
        }


    }

}
