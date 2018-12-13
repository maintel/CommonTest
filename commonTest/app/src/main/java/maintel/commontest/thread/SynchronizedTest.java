package maintel.commontest.thread;

import android.util.Log;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/30 14:39
 * 备注：
 */

public class SynchronizedTest {

    private int num = 0;
    // 这里的锁是SynchronizedTest对象锁
//    synchronized public void test(String msg) {
//        for (int i = 0; i < 5; i++) {
//            Log.e("ThreadTestActivity", msg + "    " + i);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void test(String msg) {
//        // 这里的锁是SynchronizedTest对象锁
//        synchronized (this) {
//            for (int i = 0; i < 5; i++) {
//                Log.e("ThreadTestActivity", msg + "    " + i);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    String anyString = "";

    public SynchronizedTest(String anyString) {
        this.anyString = anyString;
    }

    public void test() {
        try {
            synchronized (this) {
                System.out.println("线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "进入同步块");
                Thread.sleep(3000);
                System.out.println("线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "离开同步块");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test2() {
        try {
            synchronized (SynchronizedTest.class) {
                System.out.println("test2  线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "进入同步块");
                Thread.sleep(3000);
                System.out.println("test2  线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "离开同步块");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
