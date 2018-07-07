package com.example.hp.brahmsamaj.utility;

import com.example.hp.brahmsamaj.Brahmsamaj;

/**
 * Created by PRATIK on 08-Jun-16.
 */
public class UserPreferenceManager {

    public static void preferencePutInteger(String key, int value) {
        Brahmsamaj.getApplicationPreferenceEditor().putInt(key, value);
        Brahmsamaj.getApplicationPreferenceEditor().commit();
    }

    public static int preferenceGetInteger(String key, int defaultValue) {
        return Brahmsamaj.getApplicationPreference().getInt(key, defaultValue);
    }

    public static void preferencePutBoolean(String key, boolean value) {
        Brahmsamaj.getApplicationPreferenceEditor().putBoolean(key, value);
        Brahmsamaj.getApplicationPreferenceEditor().commit();
    }

    public static boolean preferenceGetBoolean(String key, boolean defaultValue) {
        return Brahmsamaj.getApplicationPreference().getBoolean(key, defaultValue);
    }

    public static void preferencePutString(String key, String value) {
        Brahmsamaj.getApplicationPreferenceEditor().putString(key, value);
        Brahmsamaj.getApplicationPreferenceEditor().commit();
    }

    public static String preferenceGetString(String key, String defaultValue) {
        return Brahmsamaj.getApplicationPreference().getString(key, defaultValue);
    }

    public static void preferencePutLong(String key, long value) {
        Brahmsamaj.getApplicationPreferenceEditor().putLong(key, value);
        Brahmsamaj.getApplicationPreferenceEditor().commit();
    }

    public static long preferenceGetLong(String key, long defaultValue) {
        return Brahmsamaj.getApplicationPreference().getLong(key, defaultValue);
    }

    public static void preferenceRemoveKey(String key) {
        Brahmsamaj.getApplicationPreferenceEditor().remove(key);
        Brahmsamaj.getApplicationPreferenceEditor().commit();
    }

    public static void clearPreference() {
        Brahmsamaj.getApplicationPreferenceEditor().clear();
        Brahmsamaj.getApplicationPreferenceEditor().commit();
    }

    public static Boolean contains(String key) {
        return Brahmsamaj.getApplicationPreference().contains(key);
    }

    public static String get(String key) {
        return Brahmsamaj.getApplicationPreference().getString(key, null);
    }

    public static void save(String key, String value) {
        Brahmsamaj.getApplicationPreferenceEditor().putString(key, value);
        Brahmsamaj.getApplicationPreferenceEditor().commit();
    }

}
