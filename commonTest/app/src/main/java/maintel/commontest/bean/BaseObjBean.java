package maintel.commontest.bean;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/28 10:58
 * 备注：
 */

public class BaseObjBean<T> extends BaseBean {

    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BaseObjBean<?> that = (BaseObjBean<?>) o;

        return data.equals(that.data);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BaseObjBean{" +
                "data=" + data +
                '}';
    }
}
