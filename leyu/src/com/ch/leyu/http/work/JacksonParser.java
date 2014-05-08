package com.ch.leyu.http.work;

import com.ch.leyu.http.parserinterface.BaseParser;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class JacksonParser<T> extends BaseParser<T> {

    private static ObjectMapper sObjectMapper;
    /** 解析类型对象 **/
    private Class<T> mClazz;

    /** jackson解析对象 **/

    public JacksonParser(Class<T> clazz) {
        this.mClazz = clazz;
    }

    public static void clear() {
        sObjectMapper = null;
    }

    public T parse(String rsp) throws Exception {
        JSONObject paramObject = new JSONObject(rsp);
        if(paramObject != null &&!TextUtils.isEmpty(paramObject.optString(CODE))){
            if (paramObject != null && paramObject.optString(CODE).equals(SUCCESS) ){
                JSONObject jsonObject = paramObject.optJSONObject(DATA);
                if (jsonObject != null) {
                    if (sObjectMapper == null) {
                        sObjectMapper = new ObjectMapper();
                        sObjectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                        // objectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
                        // false);
                    }
                    return sObjectMapper.readValue(jsonObject.toString(), mClazz);
                }else{

                    Log.d("JacksonParser", "没有数据可用");
                }
            }else{
                Log.d("JacksonParser", "服务器code 代码错误");
            }

        }else{

            if (sObjectMapper == null) {
                sObjectMapper = new ObjectMapper();
                sObjectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                // objectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES,
                // false);
            }
            return sObjectMapper.readValue(rsp, mClazz);

        }
        return null;
    }

    public Class<T> getResponseClass() {
        return mClazz;
    }
}
