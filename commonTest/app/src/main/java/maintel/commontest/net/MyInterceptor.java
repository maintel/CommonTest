package maintel.commontest.net;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.finalteam.toolsfinal.StringUtils;
import maintel.commontest.BuildConfig;
import maintel.commontest.utils.DeviceUtils;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.GET;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/27 16:13
 * 备注：
 */

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();
        if (request.method().equals("GET")) {
            HttpUrl url = request.url();

            Set<String> parameterNames = url.queryParameterNames();      //
            for (String key : parameterNames) {                          //循环参数列表
                if (BuildConfig.DEBUG) Log.e("MyInterceptor", key);      // 如果要对已有的参数做进一步处理可以这样拿到参数
            }                                                            //只添加的话 倒是没有必要
            String sUrl = url.toString();
            int index = sUrl.indexOf("?");
            if (index > 0) {
                sUrl = sUrl + "所需参数拼接";    //所需参数拼接 ==>就是类似于 name=123&version=12&....这些
            } else {
                sUrl = sUrl + "?" + "所需参数拼接";
            }
            request = request.newBuilder().url(sUrl).build();   //重新构建
        } else if (request.method().equals("POST")) {

            if (requestBody instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                FormBody formBody = (FormBody) requestBody;
                for (int i = 0; i < formBody.size(); i++) {    // 如果要对已有的参数做进一步处理可以这样拿到参数
                    Log.e("MyInterceptor", "encodedNames:" + formBody.encodedName(i) + " encodedValues:" + formBody.encodedValue(i));
                    builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
                builder.addEncoded("参数1", "值1");  //添加公共参数
                builder.addEncoded("参数2", "值2");
                builder.addEncoded("参数3", "值3");
                request = request.newBuilder().post(builder.build()).build();  //重新构建
            }
        }
        return chain.proceed(request);
    }
}
