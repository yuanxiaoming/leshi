package com.ch.leyu.http.work;

import com.ch.leyu.http.parserinterface.BaseParser;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class JacksonParser<T> extends BaseParser<T> {
    public static final String TAG = "FastJsonParser";
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
        if (sObjectMapper == null) {
            sObjectMapper = new ObjectMapper();
            sObjectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
        }
        switch (parseType(rsp)) {
        case BaseParserJSONObject:
            JSONObject  paramObject = new JSONObject(rsp);
            if(paramObject != null &&!TextUtils.isEmpty(paramObject.optString(CODE))){
                if (paramObject != null && paramObject.optString(CODE).equals(SUCCESS) ){
                    JSONObject jsonObject = paramObject.optJSONObject(DATA);
                    if (jsonObject != null) {
                        return sObjectMapper.readValue(jsonObject.toString(), mClazz);
                    }else{
                        Log.d(TAG, "没有数据可用");
                        return null;
                    }
                }else{
                    Log.d(TAG, "服务器code 代码错误");
                    return null;
                }

            }
            return sObjectMapper.readValue(rsp, mClazz);

        case BaseParserJSONArray:

            break;
        case BaseParserString:

            break;

        case BaseParserUnknown:
            break;
        }

        return null;
    }

    public Class<T> getResponseClass() {
        return mClazz;
    }
}
