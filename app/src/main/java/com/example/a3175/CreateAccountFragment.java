package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a3175.db.User;
import com.example.a3175.db.UserViewModel;

public class CreateAccountFragment extends Fragment {

    UserViewModel userViewModel;
    SharedPreferences.Editor editor;
    NavController navController;

    EditText editTextEmail, editTextPassword, editTextVerifyPassword;
    Button buttonCreateAccount;

    boolean isEmailValid;
    boolean isPasswordValid;

    final String TAG = "test";

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        FragmentActivity activity = requireActivity();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
        navController = Navigation.findNavController(activity, R.id.navHostFragment);

        // setup view
        editTextEmail = activity.findViewById(R.id.editTextCreateAccountEmail);
        editTextPassword = activity.findViewById(R.id.editTextCreateAccountPassword);
        editTextVerifyPassword = activity.findViewById(R.id.editTextCreateAccountVerifyPassword);
        buttonCreateAccount = activity.findViewById(R.id.buttonCreateAccount);

        isEmailValid = false;
        isPasswordValid = false;

        buttonCreateAccount.setEnabled(false);

        // validate email
        TextWatcher textWatcherForEmail = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = editTextEmail.getText().toString();
                // FIXME
                isEmailValid = !email.equals("");
                buttonCreateAccount.setEnabled(isPasswordValid && isEmailValid);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextEmail.addTextChangedListener(textWatcherForEmail);

        // validate password
        TextWatcher textWatcherForPassword = new TextWatcher() {
            String password = "";
            String verifyPassword = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = editTextPassword.getText().toString();
                verifyPassword = editTextVerifyPassword.getText().toString();
                isPasswordValid = password.length() >= 4
                        && password.equals(verifyPassword);
                buttonCreateAccount.setEnabled(isPasswordValid && isEmailValid);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextPassword.addTextChangedListener(textWatcherForPassword);
        editTextVerifyPassword.addTextChangedListener(textWatcherForPassword);

            // FIXME: cipher
        buttonCreateAccount.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            User newUser = new User(email, password);
            if (userViewModel.getUserByEmail(email).size() != 0) {
                Toast.makeText(activity, "Email already exists.", Toast.LENGTH_SHORT).show();
            } else {
                // add to db
                userViewModel.insertUsers(newUser);
                // mark as new user and will ask for password change & start info later
                int newUserId = userViewModel.getUserByEmail(email).get(0).getId();
                editor.putBoolean(getResources().getString(R.string.is_first_time_login) + newUserId, true).apply();
                navController.navigateUp();
                Toast.makeText(activity, "Account created.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}