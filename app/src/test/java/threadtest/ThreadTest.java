package threadtest;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import maintel.commontest.thread.ThreadTestActivity;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/30 14:11
 * 备注：
 */

public class ThreadTest {
//
//    @Mock
//    private ThreadTestActivity activity;

    @Before
    public void setup() {

//        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void threadTest1() {
//        activity.threadStart();
        String test = null;
        Log.d("ThreadTest", "test.equals():" + test.equals("adad"));
    }

    @Test
    public void tyrCatchTest() {
        try {
            String a = "";
            int b = test();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("printStackTrace");
        }
    }

    public int test() {
        try {
            if (1 / 0 == 0) {
                return 0;
            }
        } catch (NullPointerException e) {
            System.out.println("Exception");
        }
        return -1;
    }

    @Test
    public void testString(){
        System.out.println("0.59".compareTo("1.0t"));
    }

}
