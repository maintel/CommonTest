package maintel.commontest.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/12/9 10:06
 * 备注：
 */
@Entity
public class UserTemp extends User {
    @Id
    public Long id;

    @Generated(hash = 1401897977)
    public UserTemp(Long id) {
        this.id = id;
    }

    @Generated(hash = 644530085)
    public UserTemp() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
