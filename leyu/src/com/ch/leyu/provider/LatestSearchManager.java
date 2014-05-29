package com.ch.leyu.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
/**
 *
 * @ClassName: LatestSearchManager
 * @author xiaoming.yuan
 * @date 2014-5-22 上午10:40:55
 */
public class LatestSearchManager {
    private static DatabaseHelper mLatestSearchHelper = DatabaseHelper.getInstance();

    public static void insertSearch(LatestSearch latestSearch) {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LatestSearchTable.KEYWORD, latestSearch.getKeyword());
        values.put(LatestSearchTable.DATA, latestSearch.getmVideoSearchResponseString());
        db.insert(LatestSearchTable.TABLE_NAME, null, values);
        db.close();
    }

    public static void deleteSearch(String keyWord) {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        db.execSQL("delete from "+LatestSearchTable.TABLE_NAME+ " where "+LatestSearchTable.KEYWORD+" = ?", new Object[] { keyWord });
        db.close();
    }

    public static void deleteSearch() {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        db.execSQL("delete from "+LatestSearchTable.TABLE_NAME);
        db.close();
    }

    public static ArrayList<LatestSearch> findLatestSearchAll() {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        String sql="select * from "+LatestSearchTable.TABLE_NAME +" order by " +LatestSearchTable._ID+" desc limit 6 offset 0";

        ArrayList<LatestSearch> latestSearchsArrayList=new ArrayList<LatestSearch>();
        Cursor r = db.rawQuery(sql, null);
        while (r.moveToNext()) {
            String keyWords = r.getString(r.getColumnIndex(LatestSearchTable.KEYWORD));
            String data = r.getString(r.getColumnIndex(LatestSearchTable.DATA));
            LatestSearch latestSearch = new LatestSearch(keyWords, data);
            latestSearchsArrayList.add(latestSearch);
        }
        r.close();
        db.close();
        return latestSearchsArrayList;
    }

    public static LatestSearch findSearchByKeyWord(String keyWord) {
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

    public static void updateSearch(LatestSearch latestSearch) {
        SQLiteDatabase db = mLatestSearchHelper.getWritableDatabase();
        db.execSQL("update " + LatestSearchTable.TABLE_NAME + " set  " + LatestSearchTable.DATA
                + " = ?  where " + LatestSearchTable.KEYWORD + " = ?", new Object[] {
                latestSearch.getmVideoSearchResponseString(), latestSearch.getKeyword()
        });
        db.close();
    }

    public static void insertOrUpdateSearch(LatestSearch latestSearch) {
        LatestSearch latestSearchs = findSearchByKeyWord(latestSearch.getKeyword());
        if (latestSearchs == null) {
            insertSearch(latestSearch);
        } else {
            updateSearch(latestSearch);
        }
    }
}
