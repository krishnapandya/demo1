package com.example.hp.brahmsamaj;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.paperdb.Paper;

/**
 * Created by HP on 22-05-2018.
 */

public class Brahmsamaj extends Application {
    private static final String TAG = "Restaurant Crash";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor sharedPreferencesEditor;
    private static Context mContext;
    private static Brahmsamaj instance;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mctx) {
        mContext = mctx;
    }

    public static SharedPreferences.Editor getApplicationPreferenceEditor() {
        if (sharedPreferencesEditor == null)
            sharedPreferencesEditor = sharedPreferences.edit();

        return sharedPreferencesEditor;
    }

    public static SharedPreferences getApplicationPreference() {
        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Brahmsamaj.getContext());

        return sharedPreferences;
    }

    public static String getApplicationPackageName() {
        return mContext.getPackageName();
    }

    public static Brahmsamaj getInstance() {
        return instance;
    }

    private void initApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
        Paper.init(this);
        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPreferencesEditor = sharedPreferences.edit();
            setContext(getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
