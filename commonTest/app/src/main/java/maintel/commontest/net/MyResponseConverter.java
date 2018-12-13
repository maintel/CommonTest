package maintel.commontest.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import maintel.commontest.BuildConfig;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/10/23 13:38
 * 备注：
 */
public class MyResponseConverter<T> implements Converter<ResponseBody, T> {


    private Type type;
    Gson gson = new Gson();

    public MyResponseConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {


        String result = value.string();
        String json = "";
//        return users;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getString("retCode").equals("0000")) {
                json = jsonObject.getString("retData");
                if (BuildConfig.DEBUG) Log.e("MyResponseConverter", json);
            } else {
                return (T) jsonObject.getString("message");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            T users = gson.fromJson(json, type);
            return users;
        } finally {
            value.close();
        }
    }

}
