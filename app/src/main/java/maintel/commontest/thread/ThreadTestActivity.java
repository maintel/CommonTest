package maintel.commontest.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/30 11:26
 * 备注：
 */

public class ThreadTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("ThreadTestActivity");
        setContentView(textView);
//        threadStart();
        new ThreadTest4().start();

        new ThreadTest3().start();

    }

    public void threadStart() {
        SynchronizedTest synchronizedTest = new SynchronizedTest("maintel");
        ThreadTest1 threadTest1 = new ThreadTest1(synchronizedTest);
        threadTest1.setName("thread1");
        threadTest1.start();
        EventBus.getDefault().post("hello thread1 from main");
        ThreadTest2 threadTest2 = new ThreadTest2(synchronizedTest);
        threadTest2.setName("thread2");
        threadTest2.start();
        ThreadTest3 threadTest3 = new ThreadTest3(synchronizedTest);
        threadTest3.setName("thread3");
        threadTest3.start();
    }


    synchronized private void unSynTest(String msg) {
        for (int i = 0; i < 10; i++) {
            Log.e("ThreadTestActivity", msg + i);
        }
    }
}
