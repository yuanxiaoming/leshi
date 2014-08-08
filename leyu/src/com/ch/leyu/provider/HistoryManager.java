
package com.ch.leyu.provider;

import com.ch.leyu.responseparse.HistroyResponse;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class HistoryManager {
    private static DatabaseHelper mHistroyHelper = DatabaseHelper.getInstance();

    public static void insertHistory(String path, String title, String uid, String timeStamp) {
        SQLiteDatabase db = mHistroyHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LatestSearchTable.HISTROY_PATH, path);
        values.put(LatestSearchTable.HISTROY_TITLE, title);
        values.put(LatestSearchTable.HISTROY_VIDEO_ID, uid);
        values.put(LatestSearchTable.HISTROY_TIMESTAMP, timeStamp);
        db.insert(LatestSearchTable.HISTROY_TABLE_NAME, null, values);
        db.close();
    }
    
    public static ArrayList<HistroyResponse> findLatestSearchAll() {
        SQLiteDatabase db = mHistroyHelper.getWritableDatabase();
        String sql="select * from "+LatestSearchTable.HISTROY_TABLE_NAME +" order by " +LatestSearchTable._ID+" desc limit 100 offset 0";

        ArrayList<HistroyResponse> histroyList = new ArrayList<HistroyResponse>();
        Cursor r = db.rawQuery(sql, null);
        while (r.moveToNext()) {
            String path = r.getString(r.getColumnIndex(LatestSearchTable.HISTROY_PATH));
            String title = r.getString(r.getColumnIndex(LatestSearchTable.HISTROY_TITLE));
            String uid = r.getString(r.getColumnIndex(LatestSearchTable.HISTROY_VIDEO_ID));
            String timeStamp = r.getString(r.getColumnIndex(LatestSearchTable.HISTROY_TIMESTAMP));
            HistroyResponse histroyResponse = new HistroyResponse();
            histroyResponse.setId(uid);
            histroyResponse.setPath(path);
            histroyResponse.setTitle(title);
            histroyResponse.setTimeStamp(timeStamp);
            histroyList.add(histroyResponse);
        }
        r.close();
        db.close();
        return histroyList;
    }

}
