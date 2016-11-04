package maintel.commontest.greendaotest;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;
import maintel.commontest.base.MyApplication;
import maintel.commontest.entity.DaoSession;
import maintel.commontest.entity.User;
import maintel.commontest.entity.UserDao;
import maintel.commontest.greendaotest.adapter.GreenDaoTestAdapter;
import maintel.commontest.recycleviewtest.RecyclerViewBaseAdapter;

/**
 * 说明：greenDao测试
 * 作者：mainTel
 * 时间：2016/11/3 16:43
 * 备注：
 */
@Content(R.layout.activity_greendao_test)
public class GreenDaoTestActivity extends BaseActivity implements RecyclerViewBaseAdapter.OnItemClickListener, RecyclerViewBaseAdapter.OnItemLongClickListener {

    UserDao userDao;
    @Bind(R.id.recy_test)
    RecyclerView recy_test;

    GreenDaoTestAdapter adapter;

    @Override
    protected void initData() {
        DaoSession daoSession = MyApplication.getInterface().getDaoSession();
        userDao = daoSession.getUserDao();
        adapter = new GreenDaoTestAdapter(new ArrayList<User>(), mContext);
        recy_test.setLayoutManager(new LinearLayoutManager(mContext));
        recy_test.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        queryAll();
    }

    public void add(View v) {
        User user = new User();
        user.setName(new Random().nextInt() + "cjy");
        userDao.insert(user);
        queryAll();
    }

    public void delAll(View v) {
        userDao.deleteAll();
//        queryAll();
    }

    public void queryAll() {
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        adapter.upDate(queryBuilder.list());

    }

    public boolean update(@NotNull User user) {
        if (user == null) {
            return false;
        }
        User users = userDao.queryBuilder().where(UserDao.Properties.Id.eq(user.getId())).build().unique();
        if (users == null) {
            return false;
        } else {
            userDao.update(users);
        }
        return true;
    }

    public boolean del(@NotNull User user) {
        if (user == null) {
            return false;
        }
        User users = userDao.queryBuilder().where(UserDao.Properties.Id.eq(user.getId())).build().unique();
        if (users == null) {
            return false;
        } else {
            userDao.delete(users);
        }
        return true;
    }


    @Override
    public void onItemClick(View view, int poi) {
        User user = (User) adapter.getItemByPosition(poi);
        user.setName("修改" + new Random().nextInt());
        if ( update(user)) {
            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
            queryAll();
        } else {
            Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemLongClick(View view, int poi) {
        User user = (User) adapter.getItemByPosition(poi);
        if (del(user)) {
            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
            queryAll();
        } else {
            Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }
}
