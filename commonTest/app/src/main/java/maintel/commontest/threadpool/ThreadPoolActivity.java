package maintel.commontest.threadpool;

import android.os.Bundle;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 说明：线程池测试
 * 作者：mainTel
 * 时间：2017/4/7 10:45
 * 备注：
 */

public class ThreadPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        setContentView(textView);
    }
}
