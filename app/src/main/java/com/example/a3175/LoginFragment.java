package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

public class LoginFragment extends Fragment {
    FragmentActivity activity;
    NavController navController;
    UserViewModel userViewModel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    EditText editTextEmail, editTextPassword;
    Button buttonLogin, buttonCreateAccount;

    String TAG = "test";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        navController = Navigation.findNavController(activity, R.id.buttonLogin);
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        userViewModel = new ViewModelProvider(activity).get(UserViewModel.class);

        // if a user already logged in, go to the main fragment
        if (preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1) != -1) {
            navController.navigate(R.id.action_loginFragment_to_mainFragment);
        }

        // setup view
        editTextEmail = activity.findViewById(R.id.editTextLoginEmail);
        editTextPassword = activity.findViewById(R.id.editTextLoginPassword);

        buttonLogin = activity.findViewById(R.id.buttonLogin);
        buttonCreateAccount = activity.findViewById(R.id.buttonLoginCreateAccount);

        // FIXME
        buttonLogin.setOnClickListener(v -> {
            // verify tokens
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // tokens correct, record current user and login
            List<User> users = userViewModel.getUserByEmailPassword(email, password);
            if (users == null) {
                Toast.makeText(activity, "Invalid email / password.", Toast.LENGTH_SHORT).show();
            } else {
                // save logged in user id to shared preferences for auto-login
                int userId = users.get(0).getId();
                editor.putInt(getResources().getString(R.string.logged_in_user_id), userId).apply();

                // nav
//                Bundle bundle = new Bundle();
//                bundle.putString(loggedInUserEmail, email);

                if (preferences.getBoolean(getResources().getString(R.string.is_first_time_login) + userId, false)) {
                    // to first time login fragment
                    navController.navigate(R.id.action_loginFragment_to_firstTimeLoginFragment);
                } else {
                    // to main fragment with logged in user info
                    navController.navigate(R.id.action_loginFragment_to_mainFragment);
                }
            }

        });

        buttonCreateAccount.setOnClickListener(v -> {
            navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_loginFragment_to_createAccountFragment);
        });

    }
}