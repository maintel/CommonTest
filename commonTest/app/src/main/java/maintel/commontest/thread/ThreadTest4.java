package maintel.commontest.thread;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/4/19 14:13
 * 备注：
 */

public class ThreadTest4 extends Thread {

    int[] syn = new int[1000];

    Map<Integer, Integer> synMap = new HashMap<>();

    public ThreadTest4() {

        EventBus.getDefault().register(this);


    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 1000; i++) {
            syn[i] = i + 1;
            synMap.put(i, i);
        }
        Log.d("ThreadTest4", "Thread.currentThread():" + Thread.currentThread());
        test();
    }

    private void test() {

        Log.d("ThreadTest4", "Thread.currentThread():" + Thread.currentThread());
        Map<Integer, Integer> map = synMap;
        for (int i :
                map.values()) {
            Log.e("ThreadTest4", "ThreadTest4   " + i);
            try {
                sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (synMap.values().size() != 0) {
            test();
        }

        Log.e("ThreadTest4", "is over!!!!");
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(String msg) {
        Log.e("ThreadTest3", "ThreadTest3  ====>> " + msg.toString());
        int i = Integer.parseInt(msg);
        syn[i] = -1;
    }


}
