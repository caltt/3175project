package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavOptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.a3175.db.Overview;

public class InitializeFragment extends BaseFragment {

    EditText editTextCurrentSavings;
    Button buttonSalaryBill, buttonOK;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_initialize, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //region VIEW
        editTextCurrentSavings = activity.findViewById(R.id.editTextCurrentSavings);
        buttonSalaryBill = activity.findViewById(R.id.buttonFirstTimeManageSalary);
        buttonOK = activity.findViewById(R.id.buttonInputInformationToMain);
        //endregion

        //region BUTTON
        buttonSalaryBill.setOnClickListener(v -> {
            navController.navigate(R.id.action_initializeFragment_to_recurringTransactionFragment);
        });

        buttonOK.setOnClickListener(v -> {
            // db insert
            int currentUserId = preferences.getInt(getResources().getString(R.string.logging_in_user_id), -1);
            double savings = editTextCurrentSavings.getText().toString().isEmpty()
                    ? 0
                    : Double.parseDouble(editTextCurrentSavings.getText().toString());
            Overview newOverview = new Overview(currentUserId, 0, savings, 0);
            overviewViewModel.insertOverviews(newOverview);

            // remove need initialize flag
            // set logged in flag
            editor.remove(getResources().getString(R.string.need_initialize) + currentUserId)
                    .remove(getResources().getString(R.string.logging_in_user_id))
                    .putInt(getResources().getString(R.string.logged_in_user_id), currentUserId)
                    .apply();

            // nav to main, unable to nav back
            navController.navigate(R.id.action_initializeFragment_to_mainFragment,
                    null,
                    new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build());                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });
        //endregion
    }
}