package com.example.a3175;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.a3175.db.Overview;
import com.example.a3175.db.User;
import com.example.a3175.utils.Utils;

import java.math.BigDecimal;

public class EditUserFragment extends BaseFragment {

    TextView textViewTitle;
    EditText editTextEmail, editTextPassword, editTextVerifyPassword, editTextOldPassword;
    Button buttonOK;

    boolean isPasswordValid, isEmailValid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //region VIEW
        textViewTitle = activity.findViewById(R.id.textViewEditUserTitle);
        editTextEmail = activity.findViewById(R.id.editTextEditUserEmail);
        editTextPassword = activity.findViewById(R.id.editTextEditUserPassword);
        editTextVerifyPassword = activity.findViewById(R.id.editTextEditUserVerifyPassword);
        editTextOldPassword = activity.findViewById(R.id.editTextEditUserOldPassword);
        buttonOK = activity.findViewById(R.id.buttonEditUserOK);
        //endregion

        //region CONTEXTUAL DATA
//        currentUser = userViewModel.getById(preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1));
//        if (currentUser != null) {
//            currentUserId = currentUser.getId();
//        }
        boolean needChangePassword = preferences.getBoolean(getResources().getString(R.string.need_change_password) + currentUserId, false);
        //endregion

        //region DETERMINE FRAGMENT PURPOSE
        int previousFragmentId = navController.getPreviousBackStackEntry().getDestination().getId();
        if (previousFragmentId != R.id.mainFragment) {
            // CREATE ACCOUNT

            // view
            textViewTitle.setText(R.string.title_create_account);
            buttonOK.setText(R.string.button_text_create);
            editTextOldPassword.setVisibility(View.GONE);
            editTextEmail.requestFocus();
            inputMethodManager.showSoftInput(editTextEmail, 0);

            // button function
            buttonOK.setOnClickListener(v -> {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (userViewModel.getByEmail(email) != null) {
                    Toast.makeText(activity, "Email already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    // db insert: user
                    userViewModel.insert(new User(email, Utils.encode(password)));
                    int newUserId = userViewModel.getByEmail(email).getId();

                    // db insert: overview
                    overviewViewModel.insert(new Overview(
                            newUserId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

                    // mark as need initialize
                    editor.putBoolean(getResources().getString(R.string.need_initialize) + newUserId, true).apply();

                    // mark as need change password (admin created user)
                    if (previousFragmentId == R.id.adminFragment) {
                        editor.putBoolean(getResources().getString(R.string.need_change_password) + newUserId, true).apply();
                    }

                    // nav back & hide keyboard
                    navController.navigateUp();
                    Toast.makeText(activity, "Account created.", Toast.LENGTH_SHORT).show();
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            });

        } else {
            // LOGGED IN USER EDIT ACCOUNT

            // view
            textViewTitle.setText(R.string.title_edit_account);
            editTextPassword.setHint(R.string.hint_new_password);
            editTextVerifyPassword.setHint(R.string.hint_verify_new_password);
            editTextEmail.setEnabled(false);
            editTextOldPassword.requestFocus();


            // fill user data
            editTextEmail.setText(currentUser.getEmail());

            // button function
            buttonOK.setOnClickListener(v -> {
                String password = editTextPassword.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();

                // check old password
                if (!currentUser.getPassword().equals(Utils.encode(oldPassword))) {
                    Toast.makeText(activity, "Incorrect old password", Toast.LENGTH_SHORT).show();
                } else {
                    // db update
                    currentUser.setPassword(Utils.encode(password));
                    userViewModel.update(currentUser);

                    // nav back & hide keyboard
                    navController.navigateUp();
                    Toast.makeText(activity, "Account information updated.", Toast.LENGTH_SHORT).show();
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            });
        }
        //endregion

        //region VALIDATE INPUT & ACTIVATE BUTTON
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
        //endregion
    }
}