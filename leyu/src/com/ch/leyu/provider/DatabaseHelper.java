package com.ch.leyu.provider;

import com.ch.leyu.ui.AppContext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ClassName: DatabaseHelper
 * @author xiaoming.yuan
 * @date 2014-5-22 上午10:40:44
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static volatile DatabaseHelper sInstance;
    //数据库版本升级：1。建立最近搜索表
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "leyu.db";

    public static DatabaseHelper getInstance() {
        if (sInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseHelper(AppContext.getInstance());
                }
            }
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, DATABASE_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV >= newV) {
            return;
        }

        for (int version = oldV + 1; version <= newV; version++) {
            upgradeTo(db, version);
        }

    }

    private void upgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 1:
                db.execSQL(LatestSearchTable.CREATE_TABLE_LATESTSEARCHTABLE);
                break;

            default:
                break;
        }
    }



    /**
     * Add a column to a table using ALTER TABLE.
     * @param dbTable          name of the table
     * @param columnName       name of the column to add
     * @param columnDefinition SQL for the column definition
     */
    private void addColumn(SQLiteDatabase db, String dbTable, String columnName,String columnDefinition) {
        db.execSQL("ALTER TABLE " + dbTable + " ADD COLUMN " + columnName + " "+ columnDefinition);
    }

    private void dropTable(SQLiteDatabase db, String dbTable) {
        db.execSQL("DROP TABLE IF EXISTS " + dbTable);
    }
}
