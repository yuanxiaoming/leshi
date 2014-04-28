package com.ch.leyu.http.utils;

import java.io.File;

import android.content.Context;

import static com.ch.leyu.http.cacheservice.DBOpenHelper.DB_NAME;

import com.ch.leyu.http.cacheservice.DBOpenHelper;
import com.ch.leyu.http.cacheservice.DBOpenHelperManager;

public class CacheUtil {

    /**
    * 得到文件夹的大小 单位是以K,M,G表示
    *
    * @param path
    *            文件的路径
    * @return 文件的大小
    */
    public static String getCacheSize(Context context) {
        File file = new File(context.getCacheDir().getParentFile().getAbsolutePath() + "/databases/" + DBOpenHelper.DB_NAME);
        return FileUtil.getFileSize(file);
    }

    public static void deleteCache(Context context) {
        DBOpenHelperManager.getInstance(context).deleteCache();
    }
}
