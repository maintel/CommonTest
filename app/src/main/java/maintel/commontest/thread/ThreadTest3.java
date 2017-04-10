package maintel.commontest.thread;

import org.greenrobot.eventbus.EventBus;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/30 14:41
 * 备注：
 */

public class ThreadTest3 extends Thread {

    SynchronizedTest synchronizedTest;

    public ThreadTest3(SynchronizedTest synchronizedTest) {
        this.synchronizedTest = synchronizedTest;
    }

    @Override
    public void run() {
        synchronizedTest.test();
        EventBus.getDefault().post("hello thread1 from thread3");
    }
}
