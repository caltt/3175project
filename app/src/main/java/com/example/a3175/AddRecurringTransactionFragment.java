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
import android.widget.RadioButton;

import com.example.a3175.db.RecurringTransaction;
import com.example.a3175.db.RecurringTransactionViewModel;

public class AddRecurringTransactionFragment extends Fragment {
    FragmentActivity activity;
    SharedPreferences preferences;
    RecurringTransactionViewModel viewModel;
    NavController navController;

    EditText editTextSalaryAmount, editTextSalaryDate, editTextSalaryDescription;
    Button buttonAddRecurringTransaction;
    RadioButton radioButtonIsSalary, radioButtonIsBill;

    public AddRecurringTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recurring_transaction, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(activity).get(RecurringTransactionViewModel.class);
        navController = Navigation.findNavController(activity, R.id.navHostFragment);

        // setup view
        editTextSalaryAmount = activity.findViewById(R.id.editTextAddRecurringTransactionAmount);
        editTextSalaryDate = activity.findViewById(R.id.editTextAddRecurringTransactionDate);
        editTextSalaryDescription = activity.findViewById(R.id.editTextAddRecurringTransactionDescription);
        buttonAddRecurringTransaction = activity.findViewById(R.id.buttonAddRecurringTransaction);
        radioButtonIsSalary = activity.findViewById(R.id.radioButtonIsSalary);
        radioButtonIsBill = activity.findViewById(R.id.radioButtonIsBill);

        radioButtonIsSalary.setChecked(true);

        buttonAddRecurringTransaction.setOnClickListener(v -> {
            // add to db
            int userId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            if (userId != -1) {
                double amount = Double.parseDouble(editTextSalaryAmount.getText().toString());
                amount = radioButtonIsBill.isChecked() ? -amount : amount;
                int date = Integer.parseInt(editTextSalaryDate.getText().toString());
                String description = editTextSalaryDescription.getText().toString();
                RecurringTransaction recurringTransaction = new RecurringTransaction(userId, amount, date, description);
                viewModel.insertRecurringTransactions(recurringTransaction);
            }

            // nav back
            navController.navigateUp();
        });
    }
}