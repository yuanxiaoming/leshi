
package com.ch.leyu.preference;

import com.ch.leyu.ui.AppContext;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {

    private static final String TAG = "Settings";
    private static final String FILE_NAME = "settings";
    private static final String KEY_VERSION_CODE = "version_code";

    public static void setVersionCode(int versionCode) {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_VERSION_CODE, versionCode);
        editor.commit();
    }

    public static int getVersionCode() {
        SharedPreferences sharedPreferences = AppContext.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_VERSION_CODE, 0);
    }

}

