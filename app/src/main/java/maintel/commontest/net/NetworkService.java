package maintel.commontest.net;

import java.util.List;
import java.util.Map;

import maintel.commontest.bean.CommonBean;
import maintel.commontest.bean.LockBean;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * <p>name:</p>
 * <p>describe: 访问网络接口</p>
 *
 * @author Maintel
 * @time 2016/9/18 17:58
 */
public interface NetworkService {

    /**
     * 一般的get请求
     *
     * @param url
     * @param stringMap
     * @return
     */
    @GET
    Call<String> commonGet(@Url String url, @QueryMap Map<String, String> stringMap);

    /**
     * 常用post访问网络方法
     *
     * @param url
     * @param stringMap
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<String> commonPost(@Url String url, @FieldMap Map<String, String> stringMap);

    /**
     * 更新锁名称
     *
     * @param lockserialId
     * @param userId
     * @param lockName
     * @return
     */
    @POST("updateLockName.json")
    @FormUrlEncoded
    Call<CommonBean> updateLockName(@Field("lockserialId") String lockserialId, @Field("userId") String userId, @Field("lockName") String lockName);

    @POST("getLocklist1.json")
    @FormUrlEncoded
    Call<List<LockBean>> getLocklist(@Field("lockserialId") String lockserialId, @Field("userId") String userId, @Field("lockName") String lockName);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("test")
    Call<String> test(@Body RequestBody request);


    @POST("test")
    @FormUrlEncoded
    Call<String> test2(@Field("test") String str);
//192.168.1.59:8080/yannanlock.zip
    @GET("yannanlock.zip")
    Call<ResponseBody> downLoad();
}
