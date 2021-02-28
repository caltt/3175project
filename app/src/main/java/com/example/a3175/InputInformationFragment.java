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

import com.example.a3175.db.Overview;
import com.example.a3175.db.OverviewViewModel;

public class InputInformationFragment extends Fragment {
    FragmentActivity activity;
    OverviewViewModel overviewViewModel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavController navController;

    EditText editTextCurrentSavings;
    Button buttonManageSalaryBill, buttonInputInformationToMain;

    public InputInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_information, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        overviewViewModel = new ViewModelProvider(activity).get(OverviewViewModel.class);
        navController = Navigation.findNavController(activity, R.id.editTextCurrentSavings);

        // setup view
        editTextCurrentSavings = activity.findViewById(R.id.editTextCurrentSavings);
        buttonManageSalaryBill = activity.findViewById(R.id.buttonFirstTimeManageSalary);
        buttonInputInformationToMain = activity.findViewById(R.id.buttonInputInformationToMain);

        buttonManageSalaryBill.setOnClickListener(v -> {
            navController.navigate(R.id.action_inputInformationFragment_to_recurringTransactionFragment);
        });
        buttonInputInformationToMain.setOnClickListener(v -> {
            // insert to db
            int currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            Overview newOverview = new Overview(currentUserId, 0, Double.parseDouble(editTextCurrentSavings.getText().toString()), 0);
            overviewViewModel.insertOverviews(newOverview);
            // remove the new account flag in pref
            editor.putBoolean(getResources().getString(R.string.is_first_time_login) + currentUserId, false).apply();
            // nav to main
            navController.navigate(R.id.action_inputInformationFragment_to_mainFragment);
        });
    }
}