package maintel.commontest.greendaotest.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maintel.commontest.BuildConfig;

/**
 * 说明：数据库合并升级操作类
 * 作者：mainTel
 * 时间：2016/11/4 13:50
 * 备注：
 */
public class MergeUpdateDBHelper {

    /**
     * 调用升级方法
     *
     * @param db
     * @param daoClasses 一系列dao.class
     */
    public static void migrate(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        //1 新建临时表  会自动删除旧表
        generateTempTables(db, daoClasses);
        // 删除旧表
//        dropAllTables(db, true, daoClasses);
        //2 创建新表
        createAllTables(db, false, daoClasses);
        //3 临时表数据写入新表，删除临时表
        restoreData(db, daoClasses);
    }


    /**
     * 创建新表
     *
     * @param db
     * @param ifNotExists
     * @param daoClasses
     */
    private static void createAllTables(SQLiteDatabase db, boolean ifNotExists, Class<? extends AbstractDao<?, ?>>[] daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
    }

    /**
     * 新建临时表
     *
     * @param db
     * @param daoClasses
     */
    private static void generateTempTables(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>[] daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(new StandardDatabase(db), daoClasses[i]);
            String tableName = daoConfig.tablename;
            if (!checkTable(db, tableName))
                continue;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("alter table ")
                    .append(tableName)
                    .append(" rename to ")
                    .append(tempTableName)
                    .append(";");
            if (BuildConfig.DEBUG)
                Log.e("MergeUpdateDBHelper", insertTableStringBuilder.toString());
            db.execSQL(insertTableStringBuilder.toString());
        }
    }

    /**
     * 检测table是否存在
     *
     * @param db
     * @param tableName
     */
    private static Boolean checkTable(SQLiteDatabase db, String tableName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='").append(tableName).append("'");
        Cursor c = db.rawQuery(query.toString(), null);
        if (c.moveToNext()) {
            int count = c.getInt(0);
            if (count > 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 删除所有旧表
     *
     * @param db
     * @param ifExists
     * @param daoClasses
     */
    private static void dropAllTables(SQLiteDatabase db, boolean ifExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
    }

    /**
     * 创建根删除都在NoteDao声明了，可以直接拿过来用
     * dao class already define the sql exec method, so just invoke it
     */
    private static void reflectMethod(SQLiteDatabase db, String methodName, boolean isExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (daoClasses.length < 1) {
            return;
        }
        try {
            for (Class cls : daoClasses) {
                //根据方法名，找到声明的方法
                Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
                method.invoke(null, new StandardDatabase(db), isExists);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 临时表的数据写入新表
     *
     * @param db
     * @param daoClasses
     */
    private static void restoreData(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>[] daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(new StandardDatabase(db), daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            if (!checkTable(db, tempTableName))
                continue;
            // get all columns from tempTable, take careful to use the columns list
            List<String> columns = getColumns(db, tempTableName);
            //新表，临时表都包含的字段
            ArrayList<String> properties = new ArrayList<>(columns.size());
            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;
                if (columns.contains(columnName)) {
                    properties.add(columnName);
                }
            }
            if (properties.size() > 0) {
                final String columnSQL = TextUtils.join(",", properties);

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                db.execSQL(insertTableStringBuilder.toString());
            }
            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    private static List<String> getColumns(SQLiteDatabase db, String tableName) {
        List<String> columns = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (null == columns)
                columns = new ArrayList<>();
        }
        return columns;
    }


}
