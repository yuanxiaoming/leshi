
package com.ch.leyu.provider;

import android.provider.BaseColumns;

/**
 * @ClassName: LatestSearchTable
 * @author xiaoming.yuan
 * @date 2014-5-22 上午10:41:05
 */
public class LatestSearchTable implements BaseColumns {

    /** 搜索表 */
    public static final String SEARCH_TABLE_NAME = "LatestSearchTable";

    public static final String SEARCH_KEYWORD = "keyword";

    public static final String SEARCH_DATA = "data";

    /** 历史记录表 */
    public static final String HISTROY_TABLE_NAME = "histroyRecord";

    public static final String HISTROY_TITLE = "title";

    public static final String HISTROY_PATH = "path";

    public static final String HISTROY_VIDEO_ID = "videoId";

    public static final String HISTROY_TIMESTAMP = "timestamp";

    public static final String CREATE_TABLE_LATESTSEARCHTABLE = " create table "
            + SEARCH_TABLE_NAME + " (" + _ID + " Integer primary key autoincrement, "
            + SEARCH_KEYWORD + " text, " + SEARCH_DATA + " text " + ")";

    public static final String CREATE_TABLE_HISTROY = " create table " + HISTROY_TABLE_NAME + " ("
            + _ID + " Integer primary key autoincrement, " + HISTROY_TITLE + " text, "
            + HISTROY_PATH + " text, " + HISTROY_VIDEO_ID + " text, " + HISTROY_TIMESTAMP
            + " text " + ")";
}
