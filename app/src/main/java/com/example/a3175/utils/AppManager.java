package com.example.a3175.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a3175.R;

public class AppManager {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static NavController navController;

    public AppManager(Activity activity) {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        navController = Navigation.findNavController(activity, R.id.navHostFragment);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static SharedPreferences.Editor getEditor() {
        return editor;
    }

    public static NavController getNavController() {
        return navController;
    }
}
