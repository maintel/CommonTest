package threadtest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.Map;

/**
 * @author jieyu.chen
 * @date 2018/9/4
 */
public class JsonTest {


    @Test
    public void test() {


        String json = "{\"test\":\"aaa\",\"test2\":\"bbbb\",\"test3\":\"cccc\"}";

        Map<String, String> map = new Gson().fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());

        System.out.println(map.toString());

        for (Map.Entry<String, String> entry : map.entrySet()) {

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

        }

    }

}
