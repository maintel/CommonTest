package maintel.commontest.net;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
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
        Response response = chain.proceed(request);
        if(request.method().equals("GET")){
            HttpUrl url = request.url();
            String sUrl = url.toString();


        }else if(request.method().equals("POST")){

        }



        return response;
    }
}
