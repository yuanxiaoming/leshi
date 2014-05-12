
package com.ch.leyu.http.parserinterface;

public abstract class  BaseParser<T> {

  public  abstract T parse(String result) throws Exception;

  public  final static String  CODE="code";
  public  final static String  SUCCESS="0";
  public  final static String  DATA="data";


}
