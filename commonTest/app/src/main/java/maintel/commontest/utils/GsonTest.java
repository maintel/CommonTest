package maintel.commontest.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import maintel.commontest.BuildConfig;
import maintel.commontest.bean.LockBean;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/11/23 14:19
 * 备注：
 */
public class GsonTest {


    /**
     * connectState : 1
     * batteryQuantity : 23
     * lockserialId : YannanLock-156468313
     * lockName : room1
     */


    public static String gsonListTest() {
        Gson gson = new Gson();
        List<LockBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new LockBean(i, i, "lockserialId" + i, "lockName" + i));
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        int i = 0;
        for (LockBean bean :
                list) {
            if (BuildConfig.DEBUG) Log.e("GsonTest", gson.toJson(bean));
            stringBuffer.append(gson.toJson(bean));
            i++;
            if (i < list.size()) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("]");
        if (BuildConfig.DEBUG) Log.d("GsonTest", "stringBuffer:" + stringBuffer.toString());
        return stringBuffer.toString();
    }
}
