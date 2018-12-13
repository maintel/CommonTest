package maintel.commontest.customView;

import android.util.Log;
import android.view.View;

import maintel.commontest.BuildConfig;
import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/12/21 13:55
 * 备注：
 */
@Content(R.layout.activity_customview)
public class CustomViewActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (BuildConfig.DEBUG) Log.d("CustomViewActivity", "onClick");
    }
}
