package maintel.commontest.greendaotest;

import android.view.View;

import java.util.Random;

import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;
import maintel.commontest.base.MyApplication;
import maintel.commontest.entity.DaoSession;
import maintel.commontest.entity.User;
import maintel.commontest.entity.UserDao;

/**
 * 说明：greenDao测试
 * 作者：mainTel
 * 时间：2016/11/3 16:43
 * 备注：
 */
@Content(R.layout.activity_greendao_test)
public class GreenDaoTestActivity extends BaseActivity {

    UserDao userDao;

    @Override
    protected void initData() {
        DaoSession daoSession = MyApplication.getInterface().getDaoSession();
        userDao = daoSession.getUserDao();

    }

    public void add(View v) {
        long id = new Random().nextLong();
        User user = new User(id, id + "cjy");
        userDao.insert(user);
    }
}
