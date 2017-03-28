package maintel.commontest;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import maintel.commontest.ZipExtractor.ZipExtractorActivity;
import maintel.commontest.bean.BaseObjBean;
import maintel.commontest.bean.BaseSequenceBean;
import maintel.commontest.bean.LockBean;
import maintel.commontest.bean.User;
import maintel.commontest.customView.CustomViewActivity;
import maintel.commontest.customView.MyTitleView;
import maintel.commontest.greendaotest.GreenDaoTestActivity;
import maintel.commontest.net.MyCallBack;
import maintel.commontest.net.NetworkCallBack;
import maintel.commontest.net.NetworkUtils;
import maintel.commontest.recycleviewtest.RecycleViewTest;
import maintel.commontest.utils.DeviceUtils;
import maintel.commontest.utils.GsonTest;
import maintel.commontest.webview.FineReportTestActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyTitleView ll_title;
    //    ImageView iv_title;
    RelativeLayout.LayoutParams linearParams;
    ClipDrawable clipDrawable;
    TextView tv_title;

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
        ll_title = (MyTitleView) findViewById(R.id.ll_title);
//        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
//        clipDrawable = (ClipDrawable) iv_title.getDrawable();
//        clipDrawable.setLevel(3133);
        linearParams = (RelativeLayout.LayoutParams) ll_title.getLayoutParams();
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

//                NetworkUtils.getNetworkService()
//                        .test2(GsonTest.gsonListTest())
//                        .enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//                                if (BuildConfig.DEBUG)
//                                    Log.d("MainActivity", response.body().toString());
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable t) {
//
//                            }
//                        });

                Call<BaseSequenceBean<User>> call = NetworkUtils.getNetworkService().getUserList();
                try {
                    List<User> list = call.execute().body().getData(); //不能在主线程中执行啊
                    for (User user :
                            list) {
                        if (BuildConfig.DEBUG) Log.d("MainActivity", user.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                        .enqueue(new Callback<BaseSequenceBean<User>>() {
//                    @Override
//                    public void onResponse(Call<BaseSequenceBean<User>> call, Response<BaseSequenceBean<User>> response) {
//                        if (BuildConfig.DEBUG)
//                            Log.d("MainActivity", "resBody.getData().size():" + response.body().getData().size());
//                        List<User> list= response.body().getData();
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<BaseSequenceBean<User>> call, Throwable t) {
//
//                        if (BuildConfig.DEBUG) Log.d("MainActivity", t.getMessage().toString());
//                    }
//
//                });

                break;
            case R.id.btn_open_album_2: //联网测试2
//                NetworkUtils.getNetworkService().getLocklist("1", "1", "cjy").enqueue(new Callback<List<LockBean>>() {
//                    @Override
//                    public void onResponse(Call<List<LockBean>> call, Response<List<LockBean>> response) {
//                        List<LockBean> list = response.body();
//                        for (LockBean item :
//                                list) {
//                            if (BuildConfig.DEBUG) Log.e("MainActivity", item.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<LockBean>> call, Throwable t) {
//                        if (BuildConfig.DEBUG) Log.e("MainActivity", t.getMessage());
//                    }
//                });
//                NetworkUtils.getNetworkService().getLocklist("cyj").enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        if (BuildConfig.DEBUG) Log.d("MainActivity", response.body().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });

//                NetworkUtils.getNetworkService().getUserById("dada").enqueue(new Callback<BaseObjBean<User>>() {
//                    @Override
//                    public void onResponse(Call<BaseObjBean<User>> call, Response<BaseObjBean<User>> response) {
//                        if (BuildConfig.DEBUG) Log.d("MainActivity", response.body().getMessage());
//                        if (BuildConfig.DEBUG)
//                            Log.d("MainActivity", response.body().getData().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<BaseObjBean<User>> call, Throwable t) {
//
//                    }
//                });

                NetworkUtils.getNetworkService().getUserById("adad").enqueue(new MyCallBack<BaseObjBean<User>>() {
                    @Override
                    public void onFailure(String msg) {
                        if (BuildConfig.DEBUG) Log.d("MainActivity", msg);
                    }

                    @Override
                    public void onError(String retCode, String message) {
                        if (BuildConfig.DEBUG) Log.d("MainActivity", retCode + "  " + message);
                    }

                    @Override
                    public void onSuccess(BaseObjBean<User> res) {
                        if (BuildConfig.DEBUG) Log.d("MainActivity", res.getRetCode());
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
            case R.id.btn_go_test:
                myIntent.setClass(this, TestActivity.class);
                startActivity(myIntent);
                break;
            case R.id.tv_title:
//                showLockList();
                if (isShow) {
                    isShow = false;
//                    iv_title.setVisibility(View.GONE);
//                    mHandler.sendEmptyMessageDelayed(1002, 5);
                    ll_title.closeList();
                } else {
                    isShow = true;
                    ll_title.openList();
//                    mHandler.sendEmptyMessageDelayed(1001, 5);
//                    iv_title.setVisibility(View.VISIBLE);
////                    openFromCurrentPosition();
                }

                break;
            case R.id.btn_go_custom:
                myIntent.setClass(this, CustomViewActivity.class);
                startActivity(myIntent);
                break;
            case R.id.btn_go_zip:
                myIntent.setClass(this, ZipExtractorActivity.class);
                startActivity(myIntent);
                break;
            case R.id.btn_get_top_activity:
                ActivityManager mActivityManager;
                mActivityManager = (ActivityManager) this.getSystemService(
                        Context.ACTIVITY_SERVICE);
                DeviceUtils.isTopActivity(mActivityManager, this, "MainActivity");
                break;
        }
    }

    private ValueAnimator animator;

//    private void openFromCurrentPosition() {
//        if (animator != null) {
//            animator.cancel();
//        }
//        animator = ValueAnimator.ofFloat(0, iv_title.getHeight());
////        animator.addUpdateListener(updateListener);
////        animator.addListener(new SimpleAnimatorListener() {
////            @Override
////            public void onAnimationEnd(Animator animation) {
////                if (observer != null) {
////                    observer.onOpen();
////                }
////            }
////        });
//        animator.setDuration(2000);
//        animator.setInterpolator(new DecelerateInterpolator());
//        animator.start();
//    }
//
//    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
//
//        @Override
//        public void onAnimationUpdate(ValueAnimator animator) {
//            float v = (Float) animator.getAnimatedValue();
//            mClearRect.set(mProgress = v, 0, mWidth, getMeasuredHeight());
//            dstReel.left = mProgress - mReelWidth / 2;
//            dstReel.right = mProgress + mReelWidth / 2;
//            if (observer != null) {
//                observer.onScroll(mProgress);
//            }
//            invalidate();
//        }
//    };

    boolean isShow = false;
    int i = 0;

    private void showList() {

        for (int i = 0; i <= 50; i++) {
            this.i = i;

        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1001:
                    if (i <= 80) {
                        linearParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                        int height = 146 + i * 4;
                        linearParams.height = height;// 控件的宽强制设成30
                        ll_title.setLayoutParams(linearParams);
                        int level = (int) (((float) height / 466) * 10000);
                        clipDrawable.setLevel(level);
                        if (BuildConfig.DEBUG)
                            Log.e("MainActivity", "ll_title.getHeight():" + ll_title.getHeight() + "        " + i);
                        if (i < 80) {
                            i++;
                            mHandler.sendEmptyMessage(1001);
                        }
                    }
                    break;
                case 1002:
                    if (BuildConfig.DEBUG)
                        Log.e("MainActivity", "ll_title.getHeight():" + ll_title.getHeight() + "       " + i);
                    if (i >= 0) {
                        linearParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                        int height = 466 - ((80 - i) * 4);
                        linearParams.height = height;// 控件的宽强制设成30
                        ll_title.setLayoutParams(linearParams);
                        int level = (int) (((float) height / 466) * 10000);
                        clipDrawable.setLevel(level);
                        if (i > 0) {
                            i--;
                            mHandler.sendEmptyMessage(1002);
                        }
                    }
                    break;
            }
        }
    };

    private void closeList() {
        isShow = false;
        for (int i = 0; i <= 50; i++) {
            this.i = i;
            mHandler.sendEmptyMessageDelayed(1002, 20);
        }
    }

    PopupWindow mPopuWindow;

    /**
     * 弹出所有锁的列表
     */
    private void showLockList() {
        View view;
        ListView lockNameListView;
        if (null == mPopuWindow) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.popu_lock_list, null);
            lockNameListView = (ListView) view.findViewById(R.id.lv_lock_name);
            mPopuWindow = new PopupWindow(view, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight() / 4);
            mPopuWindow.setOutsideTouchable(true);
            mPopuWindow.setAnimationStyle(R.style.my_pop_style);
        }
        mPopuWindow.setFocusable(true);
        mPopuWindow.setOutsideTouchable(true);
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopuWindow.showAsDropDown(ll_title, 0, 0);
    }
}