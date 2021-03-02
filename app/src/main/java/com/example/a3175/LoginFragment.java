package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a3175.db.User;
import com.example.a3175.db.UserViewModel;

import java.util.List;

public class LoginFragment extends BaseFragment {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin, buttonCreateAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // if a user already logged in, go to the main fragment
        if (preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1) != -1) {
            navController.navigate(R.id.action_loginFragment_to_mainFragment);
        }

        // setup view
        editTextEmail = activity.findViewById(R.id.editTextLoginEmail);
        editTextPassword = activity.findViewById(R.id.editTextLoginPassword);
        buttonLogin = activity.findViewById(R.id.buttonLogin);
        buttonCreateAccount = activity.findViewById(R.id.buttonLoginCreateAccount);

        // FIXME cipher the password
        buttonLogin.setOnClickListener(v -> {
            // verify tokens
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // FIXME hardcoded
            // as admin: nav to admin fragment
            if ("admin".equals(email) && "admin".equals(password)) {
                navController.navigate(R.id.action_loginFragment_to_adminFragment);
            } else {
                // as user
                User user = userViewModel.getUserByEmailPassword(email, password);
                if (user == null) {
                    // login failed
                    Toast.makeText(activity, "Invalid email / password.", Toast.LENGTH_SHORT).show();
                } else {
                    // login successful
                    // save logged in user id to shared preferences for future auto-login
                    int currentUserId = user.getId();
                    editor.putInt(getResources().getString(R.string.logged_in_user_id), currentUserId).apply();

                    if (preferences.getBoolean(getResources().getString(R.string.need_change_password) + currentUserId, false)) {

                        // nav to first time login fragment
                        navController.navigate(R.id.action_loginFragment_to_editUserFragment);
                    } else if (preferences.getBoolean(getResources().getString(R.string.need_initialize) + currentUserId, false)){

                        // nav to initialize
                        navController.navigate(R.id.action_loginFragment_to_initializeFragment);
                    }else{

                        // nav to main
                        navController.navigate(R.id.action_loginFragment_to_mainFragment);
                    }
                }
            }
        });

        buttonCreateAccount.setOnClickListener(v -> {
            navController.navigate(R.id.action_loginFragment_to_editUserFragment);
        });

    }
}