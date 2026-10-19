package com.sbmatch.androtool.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SpUtils {
    private static final String DEFAULT_NAME = "SharedPref";
    private final SharedPreferences sp;

    private SpUtils(String name) {
        this(android.app.AppGlobals.getInitialApplication(), name);
    }

    private SpUtils(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance() {
        return getInstance(DEFAULT_NAME);
    }

    public static SpUtils getInstance(Context context) {
        return new SpUtils(context, DEFAULT_NAME);
    }

    public static SpUtils getInstance(String name) {
        return new SpUtils(name);
    }

    public void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }
    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }
    public void putStringSet(String key, Set<String> value) {
        sp.edit().putStringSet(key, value).apply();
    }
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sp.getStringSet(key, defValue);
    }

    public void set(String key, String value){
        putString(key, value);
    }

    public void set(String key, boolean value){
        putBoolean(key, value);
    }
    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }
}