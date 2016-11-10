package maintel.commontest.greendaotest.db;

import maintel.commontest.greendaotest.db.daomanager.UserDaoManager;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/11/10 15:30
 * 备注：
 */
public class DaoUtils {

    private static UserDaoManager userDao;

    public static UserDaoManager getUserDao() {
        if (null == userDao) {
            userDao = new UserDaoManager();
        }
        return userDao;
    }
}
