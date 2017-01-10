package maintel.commontest.greendaotest;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import maintel.commontest.BuildConfig;
import maintel.commontest.R;
import maintel.commontest.base.BaseActivity;
import maintel.commontest.base.Content;
import maintel.commontest.base.MyApplication;
import maintel.commontest.entity.DaoSession;
import maintel.commontest.entity.User;
import maintel.commontest.entity.UserDao;
import maintel.commontest.greendaotest.adapter.GreenDaoTestAdapter;
import maintel.commontest.greendaotest.db.DaoUtils;
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
    DaoSession daoSession;
    @Bind(R.id.recy_test)
    RecyclerView recy_test;

    GreenDaoTestAdapter adapter;

    @Override
    protected void initData() {
//        daoSession = MyApplication.getInterface().getDaoSession();
//        userDao = daoSession.getUserDao();
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
        int i = (int) (10 + Math.random() * (30 - 10 + 1));
        user.setAge(i + "");
        DaoUtils.getUserDao().insert(user);
        queryAll();
    }

    public void delAll(View v) {
        userDao.deleteAll();
        queryAll();
    }

    public void queryAll() {
//        daoSession.clear();
//        QueryBuilder<User> queryBuilder = userDao.queryBuilder();

        adapter.upDate(DaoUtils.getUserDao().loadAll());
    }

    public boolean update(@NotNull User user) {
        if (user == null) {
            return false;
        }
        daoSession.clear();
        long id = user.getId();
        User users = userDao.queryBuilder().where(UserDao.Properties.Id.eq(id)).build().unique();
        if (users == null) {
            return false;
        } else {
            userDao.update(user);
        }
        return true;
    }

    public boolean del(@NotNull User user) {
        if (user == null) {
            return false;
        }
//        daoSession.clear();
//        User users = userDao.queryBuilder().where(UserDao.Properties.Id.eq(user.getId())).build().unique();
//        if (users == null) {
//            return false;
//        } else {
//            userDao.delete(users);
//        }
        Map<Property, String> map = new HashMap<>();
        map.put(UserDao.Properties.Name, user.getName());
        DaoUtils.getUserDao().deleteMap(map);
        return true;
    }


    @Override
    public void onItemClick(View view, int poi) {
        User user = (User) adapter.getItemByPosition(poi);
        user.setName("修改" + new Random().nextInt());
        if (update(user)) {
            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
            queryAll();
        } else {
            Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemLongClick(View view, int poi) {
        User user = (User) adapter.getItemByPosition(poi);
//        user.setName("修改" + new Random().nextInt());
        if (del(user)) {
            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
            queryAll();
        } else {
            Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }
}
