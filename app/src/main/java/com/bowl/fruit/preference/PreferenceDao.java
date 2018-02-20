package com.bowl.fruit.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceDao{
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

}