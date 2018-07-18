package com.example.loglibrary;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

/**
 * 监听 log 的 service
 *
 * @author jieyu.chen
 * @date 2018/7/17
 */
public class LogService extends Service implements View.OnClickListener {

    private Process process;
    private BufferedReader reader = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("LOG_SERVICE", "onCreate");
        if (!isGrant()) {
            Toast.makeText(this, "没有悬浮窗权限", Toast.LENGTH_LONG).show();
            return;
        }
        myHandler = new MyHandler(this);
        new Thread(logObserve).start();
        createView();
    }

    private LinearLayout rootView;
    private Button btnShowHide;
    private ListView lvLogs;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private boolean isActive = false; //判断是否监听
    private MyHandler myHandler;


    /**
     * 创建悬浮窗
     */
    private void createView() {
        LayoutInflater inflater = LayoutInflater.from(this
                .getApplicationContext());
        rootView = (LinearLayout) inflater.inflate(R.layout.log_window, null);
        btnShowHide = rootView.findViewById(R.id.btn_show_hide);
        btnShowHide.setOnClickListener(this);
        lvLogs = rootView.findViewById(R.id.lv_log_list);

        windowManager = (WindowManager) this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        // fixme 这里是因为 在 targetSdkVersion >= 28 时不再支持 TYPE_PHONE 型，必须使用 TYPE_APPLICATION_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //TYPE_APPLICATION_OVERLAY 在应用外还会显示
            layoutParams.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        System.out.println("rootView.getWidth()::" + rootView.getWidth());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowManager.addView(rootView, layoutParams);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_show_hide) {
            if (lvLogs.getVisibility() == View.VISIBLE) {
                btnShowHide.setText("显示");
                isActive = false;
                lvLogs.setVisibility(View.GONE);
            } else {
                btnShowHide.setText("关闭");
                isActive = true;
                lvLogs.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    Runnable logObserve = new Runnable() {
        @Override
        public void run() {
            try {
                Runtime.getRuntime().exec("logcat -c").waitFor();
                // 可以通过 “TAG:VERSION *:S” 来过滤对应 tag 对应级别的日志，
                // tag 级别： V < D < I < W < E  *:tag 就是对应的当前优先级级以上级别
                process = Runtime.getRuntime().exec(new String[]{"logcat", "LOG:V *:S"});
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null && isActive) {
                    //fixme 在这个流程中做输出操作一定要做好过滤操作，否则会造成死循环最终爆掉 logcat 固有的 buffer！！！
                    //使用这种方法的不好之处就是 需要通过正则或者什么来判断读取到的日志级别
                    Message message = new Message();
                    message.what = LOG_MSG;
                    Bundle bundle = new Bundle();
                    bundle.putString("log", line);
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public final static int LOG_MSG = 998;


    static class MyHandler extends Handler {


        private WeakReference<LogService> weakReference;

        public MyHandler(LogService logService) {
            this.weakReference = new WeakReference<>(logService);
        }

        @Override
        public void handleMessage(Message msg) {
            LogService logService = weakReference.get();
            if (logService == null) return;
            switch (msg.what) {
                case LOG_MSG:
                    logService.refreshUI(msg.getData().getString("log"));
                    break;
            }
        }
    }

    private void refreshUI(String log) {

    }

    private boolean isGrant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(getApplicationContext());
        }
        return true;
    }

}
