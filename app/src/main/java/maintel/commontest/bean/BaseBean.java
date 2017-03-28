package maintel.commontest.bean;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/28 10:42
 * 备注：
 */

public class BaseBean {

    public String retCode;
    public String message;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseBean baseBean = (BaseBean) o;

        if (!retCode.equals(baseBean.retCode)) return false;
        return message.equals(baseBean.message);

    }

    @Override
    public int hashCode() {
        int result = retCode.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "retCode='" + retCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
