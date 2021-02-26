package com.example.a3175.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a3175.R;
import com.example.a3175.db.UserViewModel;

public class AppManager {
    private static SharedPreferences preferences;
    private static NavController navController;
    private static UserViewModel userViewModel;

    public AppManager(AppCompatActivity activity) {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        navController = Navigation.findNavController(activity, R.id.navHostFragment);
        userViewModel = new ViewModelProvider(activity).get(UserViewModel.class);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static NavController getNavController() {
        return navController;
    }

    public static UserViewModel getUserViewModel() {
        return userViewModel;
    }
}
