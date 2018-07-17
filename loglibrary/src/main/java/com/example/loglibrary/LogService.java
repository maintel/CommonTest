package com.example.loglibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 监听 log 的 service
 *
 * @author jieyu.chen
 * @date 2018/7/17
 */
public class LogService extends Service {

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("logcat -c").waitFor();
                    // 可以通过 “TAG:VERSION *:S” 来过滤对应 tag 对应级别的日志，
                    // tag 级别： V < D < I < W < E  *:tag 就是对应的当前优先级级以上级别
                    process = Runtime.getRuntime().exec(new String[]{"logcat", "LOG:V *:S"});
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        //fixme 不能在这个流程中做输出操作，否则会造成死循环最终爆掉 logcat 固有的 buffer！！！
                        //使用这种方法的不好之处就是 需要通过正则或者什么来判断读取到的日志级别
                        String lines = line;
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        new Thread() {
//            @Override
//            public void run() {
//                try {
//
//                    Thread.sleep(10000);
//                    Runtime.getRuntime().exec("logcat -c").waitFor();
//                    process = Runtime.getRuntime().exec(new String[]{"logcat", "*:D"});
//                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
