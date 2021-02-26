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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.a3175.db.User;
import com.example.a3175.db.UserViewModel;

public class FirstTimeLoginFragment extends Fragment {
    FragmentActivity activity;
    SharedPreferences preferences;
    UserViewModel userViewModel;
    NavController navController;

    EditText editTextNewPassword, editTextVerifyNewPassword;
    Button buttonToInputInformation;

    String TAG = "test";

    public FirstTimeLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_time_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        userViewModel = new ViewModelProvider(activity).get(UserViewModel.class);


        // setup view
        editTextNewPassword = activity.findViewById(R.id.editTextFirstLoginNewPassword);
        editTextVerifyNewPassword = activity.findViewById(R.id.editTextFirstLoginVerifyNewPassword);
        buttonToInputInformation = activity.findViewById(R.id.buttonFirstTimeLoginToInputInformation);

        buttonToInputInformation.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            String newPassword = "";
            String verifyNewPassword = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newPassword = editTextNewPassword.getText().toString();
                verifyNewPassword = editTextVerifyNewPassword.getText().toString();
                buttonToInputInformation.setEnabled(newPassword.length() >= 4
                        && newPassword.equals(verifyNewPassword));
                Log.d(TAG, "onTextChanged: "+newPassword + "" + verifyNewPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        editTextNewPassword.addTextChangedListener(textWatcher);
        editTextVerifyNewPassword.addTextChangedListener(textWatcher);

        buttonToInputInformation.setOnClickListener(v -> {
            // update db
            int currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            User currentUser = userViewModel.getUserById(currentUserId).get(0);
            Log.d(TAG, "onActivityCreated: "+currentUser.getPassword());
            //FIXME: cipher pw
            currentUser.setPassword(editTextNewPassword.getText().toString());
            userViewModel.updateUsers(currentUser);

            // nav to input info fragment
            navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_firstTimeLoginFragment_to_inputInformationFragment);
        });

    }
}