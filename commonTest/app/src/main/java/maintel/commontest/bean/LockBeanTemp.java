package maintel.commontest.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/12/9 10:04
 * 备注：
 */
@Entity
public class LockBeanTemp extends LockBean {
    @Id
    public Long id;

    @Generated(hash = 1263506700)
    public LockBeanTemp(Long id) {
        this.id = id;
    }

    @Generated(hash = 1238960559)
    public LockBeanTemp() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
