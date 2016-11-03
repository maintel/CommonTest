package maintel.commontest.net;

/**
 * <p>name:</p>
 * <p>describe:访问网络的链接</p>
 *
 * @author Maintel
 * @time 2016/9/25 20:16
 */
public interface NetworkInterface {
    // 基本Url
    String BaseUrl = "http://192.168.1.198:8080/householderinterface/";

    // 获取验证码
    String getVerCodeUrl = BaseUrl + "";
    String getVerCodePhone = "";

    // 注册用户
    String registerUserUrl = BaseUrl + "";
    String registerUserPhone = "";
    String registerUserPwd = "";

    // 登陆
//    String loginUrl = BaseUrl + "login.json";
    String loginUrl ="http://192.168.1.138:8080/yannanlock/login.json";
    //    String loginUrl = "https://yannan.tech:8543/LockInterface/gateway/SendDownBindInfo";
//    String loginUrl = "https://kyfw.12306.cn/otn/";
    String loginPhone = "";
    String loginPwd = "";

    // 获取开锁记录
    String getLockRecord = BaseUrl + "getUnlockingRcord";

    // 绑定门锁
    String bindLockUrl = BaseUrl + "";

    // 获取用户名下的门锁
    String getUserLock = BaseUrl + "getLockList";

    // 获取指静脉列表
    String getVeinKey = BaseUrl + "getLockUserlist";

    // 获取密码列表
    String getPwdKey = BaseUrl + "getPasswordlist";

    //发起注册指静脉
    String registerVeinKey = BaseUrl + "startRegisFingerInfo";

    // 发起注册密码钥匙
    String registerPwdKey = BaseUrl + "deleteFingerInfo.json";

    //删除指静脉
    String deleteFingerInfo = BaseUrl + "deleteLockUser";

    // 修改锁的名字
    String updateLockName = BaseUrl + "updateLockName";

    // 时间校准
    String checkClockTime = BaseUrl + "checkClockTime";

}
