package maintel.commontest.net;

import java.io.File;
import java.util.List;
import java.util.Map;

import maintel.commontest.bean.BaseObjBean;
import maintel.commontest.bean.BaseSequenceBean;
import maintel.commontest.bean.CommonBean;
import maintel.commontest.bean.LockBean;
import maintel.commontest.bean.User;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    @POST("/user")
    @FormUrlEncoded
    Call<List<LockBean>> getLocklist(@Field("lockserialId") String lockserialId, @Field("userId") String userId, @Field("lockName") String lockName);

    @GET("/user")
    Call<String> getLocklist(@Query("userId") String userId);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("test")
    Call<String> test(@Body RequestBody request);


    @POST("/user")
    @FormUrlEncoded
    Call<String> test2(@Field("test") String str);

    //192.168.1.59:8080/yannanlock.zip
    @GET
    Call<ResponseBody> downLoad(@Url String url);


    @GET("/user")
    Call<BaseSequenceBean<User>> getUserList();


    @GET("/user")
    Call<BaseObjBean<User>> getUserById(@Query("userId") String userId);

    @GET("/qiniu/{bucket}")
    Call<BaseObjBean<CommonBean>> getToken(@Path("bucket") String bucket);

    @POST("/qiniu")
    @FormUrlEncoded
    Call<BaseObjBean<CommonBean>> signUrl(@Field("baseUrl") String baseUrl);

    @POST("/qiniu")
    @FormUrlEncoded
    Call<ResponseBody> test3(@Field("json") String add);


}
