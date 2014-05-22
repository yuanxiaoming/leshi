package com.ch.leyu.provider;

import android.provider.BaseColumns;

/**
 *
 * @ClassName: LatestSearchTable
 * @author xiaoming.yuan
 * @date 2014-5-22 上午10:41:05
 */
public class LatestSearchTable implements BaseColumns {

    public static final String TABLE_NAME = "LatestSearchTable";

    public static final String KEYWORD = "keyword";
    public static final String DATA = "data";


    public static final String CREATE_TABLE_LATESTSEARCHTABLE = " create table " +
            TABLE_NAME + " (" +
            _ID + " Integer primary key autoincrement, " +
            KEYWORD + " text, " +
            DATA + " text " +
            ")";
}
