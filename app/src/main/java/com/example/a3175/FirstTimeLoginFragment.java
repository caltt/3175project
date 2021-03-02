package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.a3175.db.User;

public class FirstTimeLoginFragment extends BaseFragment {

    EditText editTextNewPassword, editTextVerifyNewPassword;
    Button buttonNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_time_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup view
        editTextNewPassword = activity.findViewById(R.id.editTextFirstLoginNewPassword);
        editTextVerifyNewPassword = activity.findViewById(R.id.editTextFirstLoginVerifyNewPassword);
        buttonNext = activity.findViewById(R.id.buttonFirstTimeLoginNext);

        buttonNext.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            String newPassword, verifyNewPassword;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPassword = editTextNewPassword.getText().toString();
                verifyNewPassword = editTextVerifyNewPassword.getText().toString();
                buttonNext.setEnabled(
                        newPassword.length() >= 4 && newPassword.equals(verifyNewPassword));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        editTextNewPassword.addTextChangedListener(textWatcher);
        editTextVerifyNewPassword.addTextChangedListener(textWatcher);

        buttonNext.setOnClickListener(v -> {
            // update db
            int currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            User currentUser = userViewModel.getUserById(currentUserId);
            //FIXME: cipher pw
            currentUser.setPassword(editTextNewPassword.getText().toString());
            userViewModel.updateUsers(currentUser);

            // nav to input info fragment
            navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_firstTimeLoginFragment_to_inputInformationFragment);
        });

    }
}