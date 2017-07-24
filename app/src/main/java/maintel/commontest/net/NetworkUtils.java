package maintel.commontest.net;

import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * <p>name:</p>
 * <p>describe: 网络工具类</p>
 *
 * @author Maintel
 * @time 2016/9/20 12:06
 */
public class NetworkUtils {

    public static NetworkService networkService;

    static UploadManager uploadManager;

    /**
     * 访问网络
     *
     * @return
     */
    public static NetworkService getNetworkService() {

        if (null == networkService) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new MyInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://192.168.1.59:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
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


    /**
     * 七牛上传文件
     */
    public static UploadManager uploadImageQiNiu() {
        if (uploadManager == null) {
            Configuration config = new Configuration.Builder()
                    .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                    .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                    .connectTimeout(10) // 链接超时。默认10秒
                    .responseTimeout(60) // 服务器响应超时。默认60秒
//                    .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
//                    .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                    .zone(Zone.zone1) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                    .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
            uploadManager = new UploadManager(config);
        }
        return uploadManager;
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

