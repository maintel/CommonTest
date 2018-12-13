package maintel.commontest.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 说明：Book
 * 作者：mainTel
 * 时间：2016/11/3 16:41
 * 备注：
 */
@Entity
public class Book {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    @Transient
    public int tempUsageCount; // not persisted
    @Generated(hash = 381496039)
    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1839243756)
    public Book() {
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
  
}
