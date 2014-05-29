
package com.ch.leyu.http.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ch.leyu.http.parserinterface.BaseParser;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class FastJsonParser<T> extends BaseParser<T> {
    public static final String TAG = "FastJsonParser";

    @Override
    public T parse(String rsp) throws JSONException {
        switch (parseType(rsp)) {
        case BaseParserJSONObject:
            JSONObject  paramObject = new JSONObject(rsp);
            if(paramObject != null &&!TextUtils.isEmpty(paramObject.optString(CODE))){
                if (paramObject != null && paramObject.optString(CODE).equals(SUCCESS) ){
                    JSONObject jsonObject = paramObject.optJSONObject(DATA);
                    if (jsonObject != null) {
                        return JSON.parseObject(jsonObject.toString(), new TypeReference<T>() {});
                    }else{
                        Log.d(TAG, "没有数据可用");
                        return null;
                    }
                }else{
                    Log.d(TAG, "服务器code 代码错误");
                    return null;

                }

            }
            return JSON.parseObject(rsp, new TypeReference<T>() {});

        case BaseParserJSONArray:

            break;
        case BaseParserString:

            break;

        case BaseParserUnknown:
            break;
        }

        return null;
    }
}