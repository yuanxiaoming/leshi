package com.ch.leyu.http.parserinterface;

public interface BaseParser<T> {

	T parse(String result) throws Exception;

}
