package com.ch.leyu.http.cacheservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "jhttpcache.db";
	public static SQLiteDatabase mDatabase = null;

	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	/**
	 * According to the time to determine whether the need to refresh the cache
	 * data inside
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS server_data_cache(_id INTEGER PRIMARY KEY ,url VARCHAR UNIQUE , data VARCHAR NOT NULL,time LONG NOT NULL)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

	}

}
