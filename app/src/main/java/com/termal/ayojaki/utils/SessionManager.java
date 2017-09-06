package com.termal.ayojaki.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.termal.ayojaki.App;


/**
 * Created by akbar on 12/08/17.
 */

public class SessionManager {
    private static final String TAG = "SessionManager";

    private static SharedPreferences getPref(){
        return PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }

    public static void save(String key, String value){
        Log.d(TAG, "saveCache: " + value);
        getPref().edit().putString(key, value).apply();
    }

    public static void save(String key, int value){
        Log.d(TAG, "saveCache: " + value);
        getPref().edit().putInt(key, value).apply();
    }

    public static void save(String key, Boolean value){
        Log.d(TAG, "saveCache: " + value);
        getPref().edit().putBoolean(key, value).apply();
    }

    public static Boolean checkExist(String key){
        return getPref().contains(key);
    }

    public static String grabString(String key){
        return getPref().getString(key, null);
    }

    public static Boolean grabBoolean(String key){
        return getPref().getBoolean(key, false);
    }

    public static int grabInt(String key){
        return getPref().getInt(key, 0);
    }
}
