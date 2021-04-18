package com.example.jorunal_bishe.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPref {

    public static final String KEY_TEXT_SIZE = "key.text.size";
    public static final String KEY_LINE_SPACE = "key.line.space";
    public static final String KEY_HIGHLIGHT_COLOR = "key.highlight.color";
    private static final String SHARED_NAME = "local_kv";
    private static AppPref instance;
    private SharedPreferences mPref;

    private AppPref() {
    }

    public static AppPref getInstance() {
        if (instance == null) {
            instance = new AppPref();
        }
        return instance;
    }

    public void init(Context context){
        mPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public boolean getBoolValue(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public void setBoolValue(String key, boolean value) {
        mPref.edit().putBoolean(key, value).apply();
    }

    public String getStringValue(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public void setStringValue(String key, String value) {
        mPref.edit().putString(key, value).apply();
    }

    public Long getLongValue(String key, Long defValue) {
        return mPref.getLong(key, defValue);
    }

    public void setLongValue(String key, Long value) {
        mPref.edit().putLong(key, value).apply();
    }

    public int getIntValue(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public void setIntValue(String key, int value) {
        mPref.edit().putInt(key, value).apply();
    }

    public float getFloatValue(String key, float defValue) {
        return mPref.getFloat(key, defValue);
    }

    public void setFloatValue(String key, float value) {
        mPref.edit().putFloat(key, value).apply();
    }
}
