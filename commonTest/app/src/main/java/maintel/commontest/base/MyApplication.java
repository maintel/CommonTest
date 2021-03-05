package maintel.commontest.base;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

import androidx.multidex.MultiDexApplication;
import maintel.commontest.entity.DaoMaster;
import maintel.commontest.entity.DaoSession;
import maintel.commontest.greendaotest.db.AbstractDBHelper;
import maintel.commontest.greendaotest.db.MyUpOpenHelper;

/**
 * 说明：Application
 * 作者：mainTel
 * 时间：2016/11/3 15:42
 * 备注：
 */
public class MyApplication extends MultiDexApplication {

    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;
    static MyApplication app;

    public static MyApplication getInterface() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
//        AbstractDBHelper.initDBHelper(getApplicationContext());
//        MyUpOpenHelper helper = new MyUpOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
