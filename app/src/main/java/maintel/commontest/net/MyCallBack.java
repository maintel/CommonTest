package maintel.commontest.net;

import maintel.commontest.bean.BaseBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/3/28 14:07
 * 备注：
 */

public abstract class MyCallBack<T> implements Callback<T> {

    private static String RESPONSE_SUCCESS = "0000";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            if (((BaseBean) response.body()).getRetCode().equals(RESPONSE_SUCCESS)) {
                onSuccess(response.body());
            } else {
                onError(((BaseBean) response.body()).getRetCode(), ((BaseBean) response.body()).getMessage());
            }
        } else {
            onFailure(response.code() + " " + response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (call.isCanceled()) {

        } else {
            onFailure(t.getMessage());
        }
    }

    /**
     * 访问接口失败，一般指HTTP层的错误如404，500、超时等
     * @param failMsg 错误信息
     */
    public abstract void onFailure(String failMsg);

    /**
     * 访问接口出错
     * @param retCode 错误代码
     * @param message 错误信息
     */
    public abstract void onError(String retCode, String message);

    /**
     * 访问接口成功
     * @param res 返回内容
     */
    public abstract void onSuccess(T res);


}
