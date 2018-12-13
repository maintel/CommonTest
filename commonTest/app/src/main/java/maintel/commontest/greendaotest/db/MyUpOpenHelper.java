package maintel.commontest.greendaotest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import maintel.commontest.entity.DaoMaster;
import maintel.commontest.entity.UserDao;

/**
 * 说明：数据库升级
 * 作者：mainTel
 * 时间：2016/11/4 13:47
 * 备注：
 */
public class MyUpOpenHelper extends DaoMaster.OpenHelper {

    public MyUpOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyUpOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);
        MergeUpdateDBHelper.migrate(db, UserDao.class);
    }
}
