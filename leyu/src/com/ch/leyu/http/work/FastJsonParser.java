
package com.ch.leyu.http.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ch.leyu.http.parserinterface.BaseParser;

import org.json.JSONObject;

import android.util.Log;

public class FastJsonParser<T> extends BaseParser<T> {
    public static final String TAG = "FastJsonParser";

    @Override
    public T parse(String result) throws Exception {

        JSONObject paramObject = new JSONObject(result);
        if (paramObject != null && paramObject.optInt(CODE) == SUCCESS) {
            JSONObject jsonObject = paramObject.optJSONObject(DATA);
            if (jsonObject != null) {
                return JSON.parseObject(result, new TypeReference<T>() {});
            }
        }else{
            Log.d("JacksonParser", "服务器code 代码错误");
        }
        return null;
    }
}
