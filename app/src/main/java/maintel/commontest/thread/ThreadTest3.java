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

    public ThreadTest3() {
    }

    @Override
    public void run() {
        if (synchronizedTest != null)
            synchronizedTest.test();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            EventBus.getDefault().post(i+"");
        }

    }
}
