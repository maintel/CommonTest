package maintel.commontest.bean;

import java.util.List;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/28 11:00
 * 备注：
 */

public class BaseSequenceBean<T> extends BaseBean {

    public List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseSequenceBean{" +
                "data=" + data +
                '}';
    }
}
