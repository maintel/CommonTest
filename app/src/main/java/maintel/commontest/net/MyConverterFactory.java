package maintel.commontest.net;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/10/23 13:37
 * 备注：
 */
public class MyConverterFactory extends Converter.Factory {


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new MyResponseConverter<>(type);
    }
}
