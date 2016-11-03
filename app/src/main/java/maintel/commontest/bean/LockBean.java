package maintel.commontest.bean;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/10/24 09:30
 * 备注：
 */
public class LockBean {


    /**
     * connectState : 1
     * batteryQuantity : 23
     * lockserialId : YannanLock-156468313
     * lockName : room1
     */

    public int connectState;
    public int batteryQuantity;
    public String lockserialId;
    public String lockName;

    @Override
    public String toString() {
        return "LockBean{" +
                "connectState=" + connectState +
                ", batteryQuantity=" + batteryQuantity +
                ", lockserialId='" + lockserialId + '\'' +
                ", lockName='" + lockName + '\'' +
                '}';
    }
}
