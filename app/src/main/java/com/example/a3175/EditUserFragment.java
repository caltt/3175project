package com.example.a3175;

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
import androidx.navigation.NavController;

import com.example.a3175.db.User;
import com.example.a3175.db.UserViewModel;
import com.example.a3175.utils.AppManager;

public class EditUserFragment extends Fragment {
    FragmentActivity activity;
    UserViewModel userViewModel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavController navController;

    EditText editTextEmail, editTextPassword, editTextVerifyPassword, editTextOldPassword;
    Button buttonEditUser;

    User currentUser;
    boolean isPasswordValid;
    boolean isOldPasswordValid;

    public EditUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        userViewModel = AppManager.getUserViewModel();
        preferences = AppManager.getPreferences();
        editor = preferences.edit();
        navController = AppManager.getNavController();

        // setup view
        editTextEmail = activity.findViewById(R.id.editTextEditUserEmail);
        editTextPassword = activity.findViewById(R.id.editTextEditUserPassword);
        editTextVerifyPassword = activity.findViewById(R.id.editTextEditUserVerifyPassword);
        editTextOldPassword = activity.findViewById(R.id.editTextEditUserOldPassword);
        buttonEditUser = activity.findViewById(R.id.buttonCreateAccount);

        // change view depending on previous fragment
        switch (navController.getPreviousBackStackEntry().getDestination().getId()) {
            case R.id.loginFragment:
                editTextOldPassword.setVisibility(View.GONE);
                break;
            case R.id.mainFragment:
                User currentUser = userViewModel.getUserById(preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1)).get(0);
                editTextEmail.setEnabled(false);
                editTextEmail.setText(currentUser.getEmail());
        }


        isOldPasswordValid = false;
        isPasswordValid = false;

        buttonEditUser.setEnabled(false);

        // enable button if email & password both valid
        TextWatcher textWatcherForEmail = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonEditUser.setEnabled(isPasswordValid && editTextEmail.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextEmail.addTextChangedListener(textWatcherForEmail);

        // enable button if email & password both valid
        TextWatcher textWatcherForPassword = new TextWatcher() {
            String password = "";
            String verifyPassword = "";
            String oldPassword = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = editTextPassword.getText().toString();
                verifyPassword = editTextVerifyPassword.getText().toString();
                oldPassword = editTextOldPassword.getText().toString();

                isPasswordValid = password.length() >= 4
                        && password.equals(verifyPassword)
                        && !oldPassword.isEmpty();

                buttonEditUser.setEnabled(isPasswordValid && !editTextEmail.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextPassword.addTextChangedListener(textWatcherForPassword);
        editTextVerifyPassword.addTextChangedListener(textWatcherForPassword);

        // FIXME: cipher
        buttonEditUser.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String oldPassword = editTextOldPassword.getText().toString();

            // button behaviors based on previous fragment
            switch (navController.getPreviousBackStackEntry().getDestination().getId()) {
                // user sign up
                case R.id.loginFragment:
                    if (userViewModel.getUserByEmail(email).size() != 0) {
                        Toast.makeText(activity, "Email already exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        // db insert
                        userViewModel.insertUsers(new User(email, password));

                        // mark as new user and will ask for password change & start info later
                        int newUserId = userViewModel.getUserByEmail(email).get(0).getId();
                        editor.putBoolean(getResources().getString(R.string.is_first_time_login) + newUserId, true).apply();

                        // nav
                        navController.navigateUp();
                        Toast.makeText(activity, "Account created.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                // user change password
                case R.id.mainFragment:
                    // check old password
                    currentUser = userViewModel.getUserByEmail(email).get(0);
                    if (!currentUser.getPassword().equals(oldPassword)) {
                        Toast.makeText(activity, "Incorrect old password", Toast.LENGTH_SHORT).show();
                    } else {
                        // db update
                        currentUser.setPassword(password);
                        userViewModel.updateUsers(currentUser);

                        // nav
                        navController.navigateUp();
                        Toast.makeText(activity, "Account information updated.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                // admin change password
                case R.id.adminFragment:
                    break;
            }

        });

    }
}