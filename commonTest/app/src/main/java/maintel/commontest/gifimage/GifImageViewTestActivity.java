package maintel.commontest.gifimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import maintel.commontest.R;

/**
 * @author jieyu.chen
 * @date 2018/7/25
 */
public class GifImageViewTestActivity extends Activity {

    int i = 0;
    private boolean isStop = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_test);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStop = false;
                startActivityForResult(new Intent(GifImageViewTestActivity.this, GifImageViewTest2Activity.class), 1001);
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStop = true;
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (i < 1300 && !isStop) {
            i++;
            System.err.println(i);
            startActivityForResult(new Intent(GifImageViewTestActivity.this, GifImageViewTest2Activity.class), 1001);
        }
    }
}
