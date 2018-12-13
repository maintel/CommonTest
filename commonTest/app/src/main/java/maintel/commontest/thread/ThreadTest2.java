package maintel.commontest.thread;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/30 14:41
 * 备注：
 */

public class ThreadTest2 extends Thread {

    SynchronizedTest synchronizedTest;

    public ThreadTest2(SynchronizedTest synchronizedTest) {
        this.synchronizedTest = synchronizedTest;
        EventBus.getDefault().register(this);
    }

    @Override
    public void run() {
        synchronizedTest.test2();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(String msg){
        Log.e("ThreadTest1", msg.toString());
    }
}
