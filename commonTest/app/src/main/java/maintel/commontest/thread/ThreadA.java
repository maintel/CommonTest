package maintel.commontest.thread;

/**
 * @author jieyu.chen
 * @date 2018/7/26
 */
public class ThreadA extends Thread {

    @Override
    public void run() {
        super.run();
        System.out.println(currentThread());
    }


    public void test() {
        System.out.println("test" + currentThread());
    }
}
