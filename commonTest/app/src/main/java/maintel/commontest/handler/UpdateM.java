package maintel.commontest.handler;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import maintel.commontest.BuildConfig;
import maintel.commontest.base.MyApplication;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/6/22 17:53
 * 备注：
 */

public class UpdateM implements Runnable {

    Handler handler;

    public UpdateM(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        if (BuildConfig.DEBUG) Log.e("UpdateM", "输出 ");
        Toast.makeText(MyApplication.getInterface().getApplicationContext(), "shuchu", Toast.LENGTH_SHORT).show();
        Log.e("UpdateM", "Thread.currentThread():" + Thread.currentThread());
        handler.postDelayed(this, 1000);

    }
}
