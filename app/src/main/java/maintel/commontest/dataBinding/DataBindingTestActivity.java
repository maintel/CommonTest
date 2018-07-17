package maintel.commontest.dataBinding;

import android.databinding.DataBindingUtil;
import android.view.View;

import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;
import maintel.commontest.bean.User;
import maintel.commontest.databinding.ActivityDatabindingBinding;

/**
 * @author jieyu.chen
 * @date 2018/7/10
 */
@Content(R.layout.activity_databinding)
public class DataBindingTestActivity extends BaseActivity {

    private User user;


    @Override
    protected void initData() {
        ActivityDatabindingBinding bindingTestActivity = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        user = new User();
        user.setUserName("maintel");
        bindingTestActivity.setUser(user);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                user.setUserName("jieyu.chen");
                break;
        }
    }
}
