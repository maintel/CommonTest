package maintel.commontest.handler;

import android.os.Handler;
import android.os.Message;

import maintel.commontest.base.BaseActivity;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/6/22 11:05
 * 备注：
 */

public class HandlerTestActivity extends BaseActivity {
    @Override
    protected void initData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    handler.removeMessages(1001);
//                    handler.sendEmptyMessageDelayed(1001, 2000);
//                }
//
//            }
//        }).start();
        handler.postDelayed(new UpdateM(handler), 200);
    }

    Handler handler = new Handler();


//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1001:
//                    System.out.println("1001");
//                    break;
//                case 1002:
//                    System.out.println("1002");
//                    break;
//            }
//        }
//    };
}
