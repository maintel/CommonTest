package maintel.commontest.greendaotest.db.daomanager;

import org.greenrobot.greendao.AbstractDao;

import maintel.commontest.entity.User;
import maintel.commontest.greendaotest.db.AbstractDBHelper;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/11/10 18:40
 * 备注：
 */
public class UserDaoManager extends AbstractDBHelper<User, Long> {
    
    @Override
    public AbstractDao<User, Long> getAbstractDao() {
        return daoSession.getUserDao();
    }
}
