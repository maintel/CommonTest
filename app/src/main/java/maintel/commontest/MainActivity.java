package maintel.commontest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import maintel.commontest.bean.CommonBean;
import maintel.commontest.bean.LockBean;
import maintel.commontest.greendaotest.GreenDaoTestActivity;
import maintel.commontest.net.NetworkCallBack;
import maintel.commontest.net.NetworkUtils;
import maintel.commontest.recycleviewtest.RecycleViewTest;
import maintel.commontest.utils.GsonTest;
import maintel.commontest.webview.FineReportTestActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        //透明状态栏

        //透明导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);
//        openImage();

    }


    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent();
        switch (v.getId()) {
            case R.id.btn_open_album:
                PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
                intent.setPhotoCount(9);
                intent.setShowCamera(true);
                startActivityForResult(intent, 10001);
                break;
            case R.id.btn_open_album_1: //联网测试1
//                NetworkUtils.getNetworkService().updateLockName("1", "1", "cjy").enqueue(new Callback<CommonBean>() {
//                    @Override
//                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
//                        if (BuildConfig.DEBUG) Log.e("MainActivity", response.body().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<CommonBean> call, Throwable t) {
//                        if (BuildConfig.DEBUG) Log.e("MainActivity", t.getMessage());
//                    }
//                });
//                NetworkUtils.getNetworkService()
//                        .test(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), GsonTest.gsonListTest()))
//                        .enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//
//                            }
//                        });

                NetworkUtils.getNetworkService()
                        .test2(GsonTest.gsonListTest())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                break;
            case R.id.btn_open_album_2: //联网测试2
                NetworkUtils.getNetworkService().getLocklist("1", "1", "cjy").enqueue(new Callback<List<LockBean>>() {
                    @Override
                    public void onResponse(Call<List<LockBean>> call, Response<List<LockBean>> response) {
                        List<LockBean> list = response.body();
                        for (LockBean item :
                                list) {
                            if (BuildConfig.DEBUG) Log.e("MainActivity", item.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LockBean>> call, Throwable t) {
                        if (BuildConfig.DEBUG) Log.e("MainActivity", t.getMessage());
                    }
                });
                break;
            case R.id.btn_open_album_3: //联网测试2
                NetworkUtils.getNetworkService()
                        .getLocklist("1", "1", "cjy")
                        .enqueue(new NetworkCallBack<List<LockBean>>() {
                            @Override
                            public void onSuc(List<LockBean> resBody) {
                                if (BuildConfig.DEBUG) Log.e("MainActivity", "onSuc");
                                if (BuildConfig.DEBUG) Log.d("MainActivity", "resBody:" + resBody);
                                for (LockBean bean :
                                        resBody) {
                                    if (BuildConfig.DEBUG) Log.e("MainActivity", bean.toString());
                                }
                            }

                            @Override
                            public void onFail(String msg) {
                                if (BuildConfig.DEBUG) Log.e("MainActivity", msg);
                            }
                        });
                break;
            case R.id.btn_recycle_test:
                myIntent.setClass(this, RecycleViewTest.class);
                startActivity(myIntent);
                break;
            case R.id.btn_green_dao:
                myIntent.setClass(this, GreenDaoTestActivity.class);
                startActivity(myIntent);
                break;
            case R.id.btn_fine_report:
                myIntent.setClass(this, FineReportTestActivity.class);
                startActivity(myIntent);
                break;
            case R.id.btn_gson_test:
                GsonTest.gsonListTest();
                break;
        }
    }
}

