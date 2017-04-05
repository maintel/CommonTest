package threadtest;

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

    @Mock
    private ThreadTestActivity activity;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);



    }

    @Test
    public void threadTest1(){
        activity.threadStart();
    }

}
