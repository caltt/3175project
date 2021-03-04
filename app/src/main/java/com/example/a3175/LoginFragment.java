package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavOptions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a3175.db.User;
import com.example.a3175.db.UserViewHolder;
import com.example.a3175.db.UserViewModel;
import com.example.a3175.utils.Utils;

public class LoginFragment extends BaseFragment {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin, buttonCreateAccount;

    int currentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // FIXME
        currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);

        // if a user already logged in, nav to main, unable to nav back
        if (currentUserId != -1) {
            navController.navigate(R.id.action_loginFragment_to_mainFragment,
                    null,
                    new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build());
        }

        // view
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
            // as user
            User user = userViewModel.getUserByEmailPassword(email, Utils.encode(password));

            if (user == null) {
                // login failed

                Toast.makeText(activity, "Invalid email / password.", Toast.LENGTH_SHORT).show();
            } else {
                // login successful

                if (email == "admin") {
                    // as admin

                    // nav to admin fragment
                    navController.navigate(R.id.action_loginFragment_to_adminFragment);
                } else {
                    // as user

                    int currentUserId = user.getId();

                    // set logging in id for change password & initialize fragment
                    editor.putInt(getResources().getString(R.string.logging_in_user_id), currentUserId).apply();

                    if (preferences.getBoolean(getResources().getString(R.string.need_change_password) + currentUserId, false)) {

                        // nav to first login change password
                        navController.navigate(R.id.action_loginFragment_to_firstLoginChangePasswordFragment);
                    } else if (preferences.getBoolean(getResources().getString(R.string.need_initialize) + currentUserId, false)) {

                        // nav to initialize
                        navController.navigate(R.id.action_loginFragment_to_initializeFragment);
                    } else {

                        // mark as logged in
                        editor.putInt(getResources().getString(R.string.logged_in_user_id), currentUserId).apply();

                        // nav to main, unable to nav back
                        navController.navigate(R.id.action_loginFragment_to_mainFragment,
                                null,
                                new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build());
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }

        });

        buttonCreateAccount.setOnClickListener(v -> {
            navController.navigate(R.id.action_loginFragment_to_editUserFragment);
        });

    }
}