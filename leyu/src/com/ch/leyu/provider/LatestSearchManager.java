package com.ch.leyu.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jwb on 13-12-26.
 */
public class LatestSearchManager {
    private DatabaseHelper mLatestSearchHelper = DatabaseHelper.getInstance();

    public void insertSearch(LatestSearch latestSearch) {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LatestSearchTable.KEYWORD, latestSearch.getKeyword());
        values.put(LatestSearchTable.DATA, latestSearch.getmVideoSearchResponseString());
        db.insert(LatestSearchTable.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteSearch(String keyWord) {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        db.execSQL("delete from "+LatestSearchTable.TABLE_NAME+ " where "+LatestSearchTable.KEYWORD+" = ?", new Object[] { keyWord });
        db.close();
    }

    public void deleteSearch() {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        db.execSQL("delete from "+LatestSearchTable.TABLE_NAME);
        db.close();
    }

    public LatestSearch findSearchByKeyWord(String keyWord) {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        LatestSearch latestSearch = null;
        Cursor r = db.rawQuery("select * from "+LatestSearchTable.TABLE_NAME+ " where "+LatestSearchTable.KEYWORD+" = ? ", new String[] { keyWord });
        while (r.moveToNext()) {
            String keyWords = r.getString(r.getColumnIndex(LatestSearchTable.KEYWORD));
            String data = r.getString(r.getColumnIndex(LatestSearchTable.DATA));
            latestSearch = new LatestSearch(keyWords, data);
        }
        r.close();
        db.close();
        return latestSearch;
    }

    public void updateSearch(LatestSearch latestSearch) {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        db.execSQL("update "+LatestSearchTable.TABLE_NAME+ " set  "+LatestSearchTable.DATA+ " = ?  where "+LatestSearchTable.KEYWORD+ " = ?", new Object[] { latestSearch.getmVideoSearchResponseString(), latestSearch.getKeyword() });
        db.close();
    }

    public void insertOrUpdateSearch(LatestSearch latestSearch) {
        LatestSearch latestSearchs = findSearchByKeyWord(latestSearch.getKeyword());
        if (latestSearchs == null) {
            insertSearch(latestSearchs);
        } else {
            updateSearch(latestSearchs);
        }
    }
}
