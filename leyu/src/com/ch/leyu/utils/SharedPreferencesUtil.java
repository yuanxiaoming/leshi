package com.ch.leyu.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
	private static final String SMOKEALARM_SP = "smokealarm_sp";

	private SharedPreferences mSharedPreferences;

	public SharedPreferencesUtil(Context context) {
		mSharedPreferences = context.getSharedPreferences(SMOKEALARM_SP, Context.MODE_PRIVATE);
	}

	public void putString(String key, String value) {
		mSharedPreferences.edit().putString(key, value).commit();
	}

	public String getString(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	public void putBoolean(String key, boolean value) {
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	public void putInt(String key, int value) {
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	public int getInt(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}

	public void putLong(String key, long value) {
		mSharedPreferences.edit().putLong(key, value).commit();
	}

	public void putFloat(String key, float value) {
		mSharedPreferences.edit().putFloat(key, value).commit();
	}

	public long getLong(String key, long defaultValue) {
		return mSharedPreferences.getLong(key, defaultValue);
	}

	public float getFloat(String key, float defaultValue) {
		return mSharedPreferences.getFloat(key, defaultValue);
	}
}
