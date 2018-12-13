package maintel.commontest.bean;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/10/23 13:46
 * 备注：
 */
public class CommonBean {

    /**
     * returnValue : 1
     */

    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String toString() {
        return "CommonBean{" +
                "returnValue='" + returnValue + '\'' +
                '}';
    }
}
