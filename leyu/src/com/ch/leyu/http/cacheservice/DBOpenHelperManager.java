package com.ch.leyu.http.cacheservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBOpenHelperManager {
    private DBOpenHelper dbOpenHelper;

    private DBOpenHelperManager(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context);
    }

    public static DBOpenHelperManager getInstance(Context context) {
        return new DBOpenHelperManager(context);
    }

    public void insertCache(ServerDataCache serverDataCache) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("url", serverDataCache.getUrl());
        values.put("data", serverDataCache.getServerData());
        values.put("time", serverDataCache.getTime());
        db.insert("server_data_cache", null, values);
        db.close();
    }

    public void deleteCache(String path) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from server_data_cache where url=?", new Object[] { path });
        db.close();
    }

    public void deleteCache() {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from server_data_cache");
        db.close();
    }

    public ServerDataCache findCacheByUrl(String url) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ServerDataCache cacheData = null;
        Cursor r = db.rawQuery("select * from server_data_cache where url = ? ", new String[] { url });
        while (r.moveToNext()) {
            String cache_url = r.getString(r.getColumnIndex("url"));
            String cache_data = r.getString(r.getColumnIndex("data"));
            long cache_time = r.getLong(r.getColumnIndex("time"));
            cacheData = new ServerDataCache(cache_url, cache_data, cache_time);
        }
        r.close();
        db.close();
        return cacheData;
    }

    public void updateCache(ServerDataCache serverDataCache) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update server_data_cache set  data = ? , time = ? where url = ?", new Object[] { serverDataCache.getServerData(), serverDataCache.getTime(), serverDataCache.getUrl() });
        db.close();
    }

    public void insertOrUpdateCache(ServerDataCache serverDataCache) {
        ServerDataCache cacheData = findCacheByUrl(serverDataCache.getUrl());
        if (cacheData == null) {
            insertCache(serverDataCache);
        } else {
            updateCache(serverDataCache);
        }
    }
}
