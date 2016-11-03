package maintel.commontest.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;

/**
 * 说明：Activity基类
 * 作者：mainTel
 * 时间：2016/11/3 16:44
 * 备注：
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        mContext = this;
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Class<? extends BaseActivity> attr = getClass();
        Content annotation = attr.getAnnotation(Content.class);
        if (annotation != null) {
            int value = annotation.value();
            setContentView(value);
            ButterKnife.bind(this);
        }
        initData();
    }

    protected abstract void initData();
}
