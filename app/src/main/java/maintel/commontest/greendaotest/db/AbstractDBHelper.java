package maintel.commontest.greendaotest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

import cn.finalteam.toolsfinal.StringUtils;
import maintel.commontest.entity.DaoMaster;
import maintel.commontest.entity.DaoSession;


/**
 * 说明：数据库操作
 * 作者：mainTel
 * 时间：2016/11/3 17:50
 * 备注：
 */
public abstract class AbstractDBHelper<M, K> implements IDatabase<M, K> {

    public static String DATABASE_NAME = "notes-db";
    private static MyUpOpenHelper mHelper;
    protected static DaoSession daoSession;

    /**
     * 初始化
     *
     * @param context
     */
    public static void initDBHelper(Context context) {
        mHelper = getOpenHelper(context, DATABASE_NAME);
        openWritableDb();
    }

    /**
     * 初始化 自定义数据库名
     *
     * @param context
     * @param dataBaseName
     */
    public static void initDBHelper(@NonNull Context context, @NonNull String dataBaseName) {
        mHelper = getOpenHelper(context, dataBaseName);
        openWritableDb();
    }

    protected static void openWritableDb() throws SQLiteException {
        daoSession = new DaoMaster(getWritableDatabase()).newSession();
    }

    protected static void openReadableDb() throws SQLiteException {
        daoSession = new DaoMaster(getReadableDatabase()).newSession();
    }

    private static SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }

    private static SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    private static MyUpOpenHelper getOpenHelper(@NonNull Context context, @Nullable String dataBaseName) {
        closeDbConnections();
        return new MyUpOpenHelper(context, dataBaseName, null);
    }

    /**
     * 关闭数据库
     */
    public static void closeDbConnections() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public void clearDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    @Override
    public boolean insert(@NonNull M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().insert(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(@NonNull M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().delete(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(K key) {
        try {
            if (StringUtils.isEmpty(key.toString()))
                return false;
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(@NonNull List<M> ms) {
        try {
            if (ms == null || ms.size() == 0)
                return false;
            getAbstractDao().deleteInTx(ms);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKeyInTx(K... key) {
        try {
            getAbstractDao().deleteByKeyInTx(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NonNull M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().insertOrReplace(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(@NonNull M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().update(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx(@NonNull M... m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().updateInTx(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateList(@NonNull List<M> ms) {
        try {
            if (ms == null || ms.size() == 0)
                return false;
            getAbstractDao().updateInTx(ms);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public
    @NonNull
    M selectByPrimaryKey(K key) {
        try {
            openReadableDb();
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public List<M> loadAll() {
        openReadableDb();
        return getAbstractDao().loadAll();
    }

    @Override
    public boolean refresh(@NonNull M m) {
        try {
            if (m == null)
                return false;
            getAbstractDao().refresh(m);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean dropDatabase() {
        try {
            // DaoMaster.dropAllTables(database, true); // drops all tables
            // mHelper.onCreate(database); // creates the tables
//          daoSession.deleteAll(BankCardBean.class); // clear all elements
            // from
            // a table
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            daoSession.runInTx(runnable);
        } catch (SQLiteException e) {
        }
    }

    @Override
    public boolean insertList(@NonNull List<M> ms) {
        try {
            if (ms == null || ms.size() == 0)
                return false;
            getAbstractDao().insertInTx(ms);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplaceList(@NonNull List<M> ms) {
        try {
            if (ms == null || ms.size() == 0)
                return false;
            getAbstractDao().insertOrReplaceInTx(ms);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public QueryBuilder<M> getQueryBuilder() {
        return getAbstractDao().queryBuilder();
    }

    @Override
    public List<M> queryRaw(String where, String... selectionArg) {
        return getAbstractDao().queryRaw(where, selectionArg);
    }


    public Query<M> queryRawCreate(String where, Object... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreate(where, selectionArg);
    }

    public Query<M> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
    }

    /**
     * 获取Dao
     *
     * @return
     */
    public abstract AbstractDao<M, K> getAbstractDao();
}
