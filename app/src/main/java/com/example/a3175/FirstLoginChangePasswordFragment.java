package com.example.a3175;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a3175.db.User;
import com.example.a3175.utils.Utils;

public class FirstLoginChangePasswordFragment extends BaseFragment {

    TextView textViewTitle;
    EditText editTextEmail, editTextOldPassword, editTextNewPassword, editTextVerifyNewPassword;
    Button buttonOK;

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
        textViewTitle = activity.findViewById(R.id.textViewEditUserTitle);
        editTextEmail = activity.findViewById(R.id.editTextEditUserEmail);
        editTextOldPassword = activity.findViewById(R.id.editTextEditUserOldPassword);
        editTextNewPassword = activity.findViewById(R.id.editTextEditUserPassword);
        editTextVerifyNewPassword = activity.findViewById(R.id.editTextEditUserVerifyPassword);
        buttonOK = activity.findViewById(R.id.buttonEditUserOK);

        // view
        textViewTitle.setText(R.string.title_first_login_set_password);
        editTextNewPassword.setHint(R.string.hint_new_password);
        editTextVerifyNewPassword.setHint(R.string.hint_verify_new_password);
        editTextEmail.setVisibility(View.GONE);
        editTextOldPassword.setVisibility(View.GONE);
        buttonOK.setEnabled(false);

        // valid password & activate button
        TextWatcher textWatcher = new TextWatcher() {
            String newPassword, verifyNewPassword;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPassword = editTextNewPassword.getText().toString();
                verifyNewPassword = editTextVerifyNewPassword.getText().toString();
                buttonOK.setEnabled(newPassword.length() >= 4 && newPassword.equals(verifyNewPassword));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        editTextNewPassword.addTextChangedListener(textWatcher);
        editTextVerifyNewPassword.addTextChangedListener(textWatcher);

        // button
        buttonOK.setOnClickListener(v -> {

            // update db
            int currentUserId = preferences.getInt(getResources().getString(R.string.logging_in_user_id), -1);
            User currentUser = userViewModel.getUserById(currentUserId);
            String password = editTextNewPassword.getText().toString();

            currentUser.setPassword(Utils.encode(password));
            userViewModel.updateUsers(currentUser);

            // remove flag
            editor.remove(getResources().getString(R.string.need_change_password) + currentUserId).apply();

            // nav to initialize, unable to nav back
            navController.navigate(R.id.action_firstLoginChangePasswordFragment_to_initializeFragment,
                    null,
                    new NavOptions.Builder().setPopUpTo(R.id.firstLoginChangePasswordFragment, true).build());
        });

        // when nav back with phone button,
//        activity.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                editor.remove(getResources().getString(R.string.logged_in_user_id)).apply();
//                navController.navigateUp();
//            }
//        });

    }
}