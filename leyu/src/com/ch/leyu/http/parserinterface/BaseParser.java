package com.ch.leyu.http.parserinterface;

public abstract  class BaseParser<T> {

    protected static final String CODE = "code";

    protected static final String DATA = "data";

    protected static final String MSG = "msg";

    protected static final int SUCCESS = 0;

    public abstract T parse(String result) throws Exception;

}
