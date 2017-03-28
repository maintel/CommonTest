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
        onFailure(t.getMessage());
    }

    public abstract void onFailure(String failMsg);

    public abstract void onError(String retCode, String message);

    public abstract void onSuccess(T res);


}
