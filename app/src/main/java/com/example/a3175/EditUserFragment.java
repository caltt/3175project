package com.example.a3175;

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

import com.example.a3175.db.User;

public class EditUserFragment extends BaseFragment {

    EditText editTextEmail, editTextPassword, editTextVerifyPassword, editTextOldPassword;
    Button buttonOK;

    User currentUser;
    boolean isPasswordValid, isEmailValid;
    int currentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup view
        editTextEmail = activity.findViewById(R.id.editTextEditUserEmail);
        editTextPassword = activity.findViewById(R.id.editTextEditUserPassword);
        editTextVerifyPassword = activity.findViewById(R.id.editTextEditUserVerifyPassword);
        editTextOldPassword = activity.findViewById(R.id.editTextEditUserOldPassword);
        buttonOK = activity.findViewById(R.id.buttonEditUserOK);

        // determine fragment purpose based on previous fragment
        currentUser = userViewModel.getUserById(preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1));
        if (currentUser != null) {
            currentUserId = currentUser.getId();
        }
        boolean needChangePassword = preferences.getBoolean(getResources().getString(R.string.need_change_password) + currentUserId, false);

        int previousFragmentId = navController.getPreviousBackStackEntry().getDestination().getId();
        if (previousFragmentId == R.id.loginFragment && needChangePassword) {
            // ADMIN CREATED ACCOUNT CHANGE PASSWORD FOR FIRST LOGIN

            // view
            editTextEmail.setVisibility(View.GONE);
            editTextOldPassword.setVisibility(View.GONE);

            // FIXME: cipher
            // button function
            buttonOK.setOnClickListener(v -> {
                String password = editTextPassword.getText().toString();
                // db update
                currentUser.setPassword(password);
                userViewModel.updateUsers(currentUser);

                // remove flag
                editor.remove(getResources().getString(R.string.need_change_password) + currentUserId).apply();

                // nav
                navController.navigate(R.id.action_editUserFragment_to_initializeFragment);
            });
        } else if (previousFragmentId == R.id.loginFragment) {
            // USER CREATE ACCOUNT

            // view
            editTextOldPassword.setVisibility(View.GONE);

            // FIXME: cipher
            // button function
            buttonOK.setOnClickListener(v -> {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (userViewModel.getUserByEmail(email) != null) {
                    Toast.makeText(activity, "Email already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    // db insert
                    userViewModel.insertUsers(new User(email, password));

                    // need initialize later
                    int newUserId = userViewModel.getUserByEmail(email).getId();
                    editor.putBoolean(getResources().getString(R.string.need_initialize) + newUserId, true)
                            .apply();

                    // nav
                    navController.navigateUp();
                    Toast.makeText(activity, "Account created.", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (previousFragmentId == R.id.adminFragment) {
            // ADMIN CREATE ACCOUNT

            // view
            editTextOldPassword.setVisibility(View.GONE);

            // FIXME: cipher
            // button function
            buttonOK.setOnClickListener(v -> {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (userViewModel.getUserByEmail(email) != null) {
                    Toast.makeText(activity, "Email already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    // db insert
                    userViewModel.insertUsers(new User(email, password));

                    // need change password & initialize later
                    int newUserId = userViewModel.getUserByEmail(email).getId();
                    editor.putBoolean(getResources().getString(R.string.need_change_password) + newUserId, true)
                            .putBoolean(getResources().getString(R.string.need_initialize) + newUserId, true)
                            .apply();

                    // nav
                    navController.navigateUp();
                    Toast.makeText(activity, "Account created.", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (previousFragmentId == R.id.mainFragment) {
            // LOGGED IN USER EDIT ACCOUNT

            // view
            editTextEmail.setEnabled(false);

            // fill user data
            editTextEmail.setText(currentUser.getEmail());

            // FIXME: cipher
            // button function
            buttonOK.setOnClickListener(v -> {
                String password = editTextPassword.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();

                // check old password
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
            });
        }

        // edit text validation
        // only activate ok button when valid

        buttonOK.setEnabled(false);

        // initial values
        // ensure button activation is correct before any edit text change
        String password = editTextPassword.getText().toString();
        String verifyPassword = editTextVerifyPassword.getText().toString();
        String oldPassword = editTextOldPassword.getText().toString();

        isEmailValid = editTextEmail.getVisibility() == View.GONE || !editTextEmail.getText().toString().isEmpty();
        isPasswordValid = password.length() >= 4
                && password.equals(verifyPassword)
                && (editTextOldPassword.getVisibility() == View.GONE || !oldPassword.isEmpty());

        // validate email
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = editTextEmail.getVisibility() == View.GONE || !editTextEmail.getText().toString().isEmpty();
                buttonOK.setEnabled(isEmailValid && isPasswordValid);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // validate password
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
//
                isPasswordValid = password.length() >= 4
                        && password.equals(verifyPassword)
                        && (editTextOldPassword.getVisibility() == View.GONE || !oldPassword.isEmpty());

                buttonOK.setEnabled(isEmailValid && isPasswordValid);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextPassword.addTextChangedListener(textWatcherForPassword);
        editTextVerifyPassword.addTextChangedListener(textWatcherForPassword);

    }
}