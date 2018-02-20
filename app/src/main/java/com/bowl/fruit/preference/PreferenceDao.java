package com.bowl.fruit.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceDao {

    private static final String SP_NAME = "SharedPreference_Fruit";
    private SharedPreferences preferences;

    private static class singleton {
        private static PreferenceDao instance = new PreferenceDao();
    }

    private PreferenceDao() {
        preferences = ContextHolder.getAppContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static PreferenceDao getInstance() {
        return singleton.instance;
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public boolean putString(String key, String value) {
        return preferences.edit().putString(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public boolean putBoolean(String key, boolean value) {
        return preferences.edit().putBoolean(key, value).commit();
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public boolean putLong(String key, long value) {
        return preferences.edit().putLong(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public boolean putInt(String key, int value) {
        return preferences.edit().putInt(key, value).commit();
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public boolean putFloat(String key, float value) {
        return preferences.edit().putFloat(key, value).commit();
    }

    public boolean clearData() {
        return preferences.edit().clear().commit();
    }
}
