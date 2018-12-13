package maintel.commontest.gifimage;

import android.os.Handler;
import android.os.Message;

import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;

/**
 * @author jieyu.chen
 * @date 2018/7/25
 */
@Content(R.layout.activity_gif_test)
public class GifImageViewTest2Activity extends BaseActivity {
    @Override
    protected void initData() {
        handler.sendEmptyMessageDelayed(1000, 150);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1000) {
                finish();
            }
        }
    };
}
