package maintel.commontest.net;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * <p>name:</p>
 * <p>describe: 网络工具类</p>
 *
 * @author Maintel
 * @time 2016/9/20 12:06
 */
public class NetworkUtils {

    public static NetworkService networkService;


    /**
     * 访问网络
     *
     * @return
     */
    public static NetworkService getNetworkService() {

        if (null == networkService) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .sslSocketFactory(getSSLSocketFactory(MyApplication.getInterface().getApplicationContext(), certificates))
//                    .sslSocketFactory(getSSLSocketFactory())
//                    .hostnameVerifier(get)
                    .connectTimeout(15, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://192.168.1.138:8080/yannanlock/")
                    .addConverterFactory(new MyConverterFactory())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            networkService = retrofit.create(NetworkService.class);
        }
        return networkService;
    }

    /**
     * 普通post请求
     *
     * @param url 请求地址
     * @param map 参数列表
     * @param e   回调
     */
    public static void post(String url, Map<String, String> map, NetworkCallBack<String> e) {
        if (null == map) {
            map = new HashMap<>();
        }
        getNetworkService().commonPost(url, map).enqueue(e);
    }

    public interface ICommonPostCallBack {
        void onSuc();

        void onFail();
    }
//
//    /**
//     * 一个通用的链接的访问方法
//     *
//     * @param url
//     * @param map
//     * @return
//     */
//    public static boolean commonPost(String url, Map<String, String> map, final ICommonPostCallBack e) {
//        if (null == map) {
//            map = new HashMap<>();
//        }
//        final boolean[] isSuc = {false};
//        getNetworkService().commonPost(url, map).enqueue(new NetworkCallBack<String>() {
//            @Override
//            public void onSuc(String resBody) {
//                if (resBody != null) {
//                    CommonResponse response = null;
//                    try {
//                        response = new Gson().fromJson(resBody, CommonResponse.class);
//                        if (response.returnValue == 1) {
//                            isSuc[0] = true;
//                            e.onSuc();
//                        } else {
//                            isSuc[0] = false;
//                            e.onFail();
//                        }
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                        isSuc[0] = false;
//                        e.onFail();
//                    }
//
//                } else {
//                    isSuc[0] = false;
//                    e.onFail();
//                }
//            }
//
//            @Override
//            public void onFail(String msg) {
//                isSuc[0] = false;
//                e.onFail();
//            }
//        });
//        return isSuc[0];
//    }

//    public static void loadImage(Context context, String url, ImageView view) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.mipmap.ic_launcher) //默认图片
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.mipmap.ic_launcher)  //加载错误图片
//                .into(view);
//    }

}

