
package com.ch.leyu.http.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ch.leyu.http.parserinterface.BaseParser;

public class FastJsonParser<T> implements BaseParser<T> {
    public static final String TAG = "FastJsonParser";

    @Override
    public T parse(String result) throws Exception {
        return JSON.parseObject(result, new TypeReference<T>() {
        });
    }
}
