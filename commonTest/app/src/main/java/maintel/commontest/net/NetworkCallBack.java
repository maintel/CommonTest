package maintel.commontest.net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <p>name:</p>
 * <p>describe: 网络请求返回类</p>
 *
 * @author Maintel
 * @time 2016/9/20 15:20
 */
public abstract class NetworkCallBack<T> implements Callback<T> {
    
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            T test = response.body();
            onSuc(test);
        } catch (Exception e) {
            e.printStackTrace();
            onFail((String) response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFail(t.getMessage());
    }

    public abstract void onSuc(T resBody);

    public abstract void onFail(String msg);
}
