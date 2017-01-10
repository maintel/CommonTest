package maintel.commontest.ZipExtractor;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import maintel.commontest.BuildConfig;
import maintel.commontest.net.NetworkService;
import maintel.commontest.net.ProgressResponseBody;
import maintel.commontest.net.ProgressResponseListener;
import maintel.commontest.utils.FileUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/1/9 13:49
 * 备注：
 */

public class ZipExtractorPresenter implements ZipExtractorContract.Presenter {

    public static NetworkService networkService;

    /**
     * 访问网络
     *
     * @return
     */
    public static NetworkService getNetworkService(final ProgressResponseListener responseListener) {

        if (null == networkService) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public okhttp3.Response intercept(Chain chain) throws IOException {
                                    //拦截
                                    okhttp3.Response originalResponse = chain.proceed(chain.request());

                                    //包装响应体并返回
                                    return originalResponse.newBuilder()
                                            .body(new ProgressResponseBody(originalResponse.body(), responseListener))
                                            .build();
                                }
                            }
                    )
//                    .sslSocketFactory(getSSLSocketFactory(MyApplication.getInterface().getApplicationContext(), certificates))
//                    .sslSocketFactory(getSSLSocketFactory())
//                    .hostnameVerifier(get)
                    .connectTimeout(15, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://192.168.1.59:8080/")
//                    .baseUrl("http://10.0.2.2:8080/")
//                    .addConverterFactory(new MyConverterFactory())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            networkService = retrofit.create(NetworkService.class);
        }
        return networkService;
    }


    ZipExtractorContract.View zipExtractorView;
    Context context;


    public ZipExtractorPresenter(ZipExtractorContract.View zipExtractorView, Context context) {
        this.zipExtractorView = zipExtractorView;
        this.context = context;
        zipExtractorView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void zipExtractor() {
        ZipExtractorTask task = new ZipExtractorTask("/sdcard/commontest/test.zip", "/sdcard/commontest/test/", context, true);
        task.execute();
    }

    @Override
    public void downLoad() {
        getNetworkService(new ProgressResponseListener() {
            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                if (BuildConfig.DEBUG)
                    Log.e("ZipExtractorPresenter", "contentLength:" + contentLength);
                if (BuildConfig.DEBUG) Log.e("ZipExtractorPresenter", "bytesRead:" + bytesRead);
            }
        }).downLoad().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (BuildConfig.DEBUG) Log.e("ZipExtractorPresenter", "onResponse");
                FileUtils.saveFile(response.body().byteStream(), "/sdcard/commontest", "test." + response.body().contentType().subtype());
                zipExtractorView.setMessage("load success! /n path:/sdcard/commontest" + "test." + response.body().contentType().subtype());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (BuildConfig.DEBUG) Log.e("ZipExtractorPresenter", "onFailure");
            }
        });
    }
}
