package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.a3175.db.Salary;
import com.example.a3175.db.SalaryViewModel;

public class AddSalaryFragment extends Fragment {
    FragmentActivity activity;
    SharedPreferences preferences;
    SalaryViewModel salaryViewModel;

    EditText editTextSalaryAmount, editTextSalaryDate, getEditTextSalaryDescription;
    Button buttonAddSalary;

    public AddSalaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_salary, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        salaryViewModel = new ViewModelProvider(activity).get(SalaryViewModel.class);

        // setup view
        editTextSalaryAmount = activity.findViewById(R.id.editTextAddSalaryAmount);
        editTextSalaryDate = activity.findViewById(R.id.editTextAddSalaryDate);
        getEditTextSalaryDescription = activity.findViewById(R.id.editTextAddSalaryDescription);
        buttonAddSalary = activity.findViewById(R.id.buttonAddSalaryOK);

        buttonAddSalary.setOnClickListener(v -> {
            // add to db
            int userId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            if (userId != -1) {
                double amount = Double.parseDouble(editTextSalaryAmount.getText().toString());
                int date = Integer.parseInt(editTextSalaryDate.getText().toString());
                String description = getEditTextSalaryDescription.getText().toString();

                Salary salary = new Salary(userId, amount, date, description);
                salaryViewModel.insertSalaries(salary);
            }
        });
    }
}