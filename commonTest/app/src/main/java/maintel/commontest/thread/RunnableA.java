package maintel.commontest.thread;

/**
 * @author jieyu.chen
 * @date 2018/7/26
 */
public class RunnableA implements Runnable {

    public void test() {
        System.out.println(Thread.currentThread());
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread());
    }
}
