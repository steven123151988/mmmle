package com.daking.sports.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存单条字段所需类
 */
public class SharePreferencesUtil {

    private static final String MAIN_INIT_VALUE = "lottery";
    
    public static void addString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        return context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).getString(key, defValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    public static void addBoolean(Context context, String key, boolean value) {
        context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    public static void addInteger(Context context, String key, int value) {
        context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
    }

    public static int getInteger(Context context, String key, int defValue) {
        return context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).getInt(key, defValue);
    }

    public static void deleteData(Context context) {
        context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).edit().clear().commit();
    }
}
