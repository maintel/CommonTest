package maintel.commontest.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 说明：User类
 * 作者：mainTel
 * 时间：2016/11/3 16:41
 * 备注：
 */
@Entity
public class User {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String phone;
    public String age;
    @Transient
    public int tempUsageCount; // not persisted
    @Generated(hash = 24114832)
    public User(Long id, String name, String phone, String age) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
}
