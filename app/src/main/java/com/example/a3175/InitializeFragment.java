package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;

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

        // setup view
        editTextCurrentSavings = activity.findViewById(R.id.editTextCurrentSavings);
        buttonSalaryBill = activity.findViewById(R.id.buttonFirstTimeManageSalary);
        buttonOK = activity.findViewById(R.id.buttonInputInformationToMain);

        buttonSalaryBill.setOnClickListener(v -> {
            navController.navigate(R.id.action_inputInformationFragment_to_recurringTransactionFragment);
        });

        buttonOK.setOnClickListener(v -> {
            // insert to db
            int currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            double savings = editTextCurrentSavings.getText().toString().isEmpty()
                    ? 0
                    : Double.parseDouble(editTextCurrentSavings.getText().toString());
            Overview newOverview = new Overview(currentUserId, 0, savings, 0);
            overviewViewModel.insertOverviews(newOverview);

            // remove need initial flag
            editor.remove(getResources().getString(R.string.need_initialize) + currentUserId).apply();

            // nav to main
            navController.navigate(R.id.action_initializeFragment_to_mainFragment);
        });
    }
}