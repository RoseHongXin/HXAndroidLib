package hx.req;

import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RoseHongXin on 2016/5/9.
 *
 * For Retrofit 2.0 JacksonConverterFactory
 */

public class ReqParams {

    /**
     * 构造请求参数，0，2，4，6.。。。。等为key；
     * 1,3,5,7.....等为值
     *
     * @param data
     */
    public static Map<String, String> getParamsAsMap(String... data){
        Map<String, String> map = new HashMap<>();
        //if (data == null) return map;
        //if (data.length % 2 != 0) return map;
        for (int i = 0; i < data.length / 2; i++) {
            if (TextUtils.isEmpty(data[i * 2 + 1])) continue;
            map.put(data[i * 2], data[i * 2 + 1]);
        }
        return map;
    }

    public static ObjectNode getParamsAsJson(String... data) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        //if (data == null) return null;
        //if (data.length % 2 != 0) return json;
        for (int i = 0; i < data.length / 2; i++) {
            if (TextUtils.isEmpty(data[i * 2 + 1])) continue;
            json.put(data[i * 2], data[i * 2 + 1]);
        }
        return json;
    }

}
