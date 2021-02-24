package com.example.a3175;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.a3175.utils.AppManager;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        new AppManager(this);
        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();
        navController = Navigation.findNavController(this, R.id.navHostFragment);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public NavController getNavController() {
        return navController;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
}