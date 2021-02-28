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

public class EditRecurringTransactionFragment extends Fragment {
    private static final String TAG = "test";
    FragmentActivity activity;
    SharedPreferences preferences;
    RecurringTransactionViewModel viewModel;
    NavController navController;

    EditText editTextSalaryAmount, editTextSalaryDate, editTextSalaryDescription;
    Button buttonEditRecurringTransaction;
    RadioButton radioButtonIsSalary, radioButtonIsBill;

    RecurringTransaction currentRecurringTransaction;
    int currentRecurringTransactionId;

    public EditRecurringTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_recurring_transaction, container, false);
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
        buttonEditRecurringTransaction = activity.findViewById(R.id.buttonAddRecurringTransaction);
        radioButtonIsSalary = activity.findViewById(R.id.radioButtonIsSalary);
        radioButtonIsBill = activity.findViewById(R.id.radioButtonIsBill);

        radioButtonIsSalary.setChecked(true);

        // in case of edit
        if (getArguments() != null) {
            // get current transaction id
            currentRecurringTransactionId = getArguments().getInt("recurringTransactionId", -1);

            if (currentRecurringTransactionId != -1) {
                // get transaction by id
                currentRecurringTransaction = viewModel.getRecurringTransactionById(currentRecurringTransactionId).get(0);

                // fill editText with transaction data
                double amount = currentRecurringTransaction.getAmount();
                if (amount < 0) {
                    radioButtonIsBill.setChecked(true);
                } else {
                    radioButtonIsSalary.setChecked(true);
                }
                editTextSalaryAmount.setText(String.valueOf(Math.abs(amount)));
                editTextSalaryDate.setText(String.valueOf(currentRecurringTransaction.getDate()));
                editTextSalaryDescription.setText(String.valueOf(currentRecurringTransaction.getDescription()));
            }
        }

        // button
        buttonEditRecurringTransaction.setOnClickListener(v -> {
            int userId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            double amount = Double.parseDouble(editTextSalaryAmount.getText().toString());
            amount = radioButtonIsBill.isChecked() ? -amount : amount;
            int date = Integer.parseInt(editTextSalaryDate.getText().toString());
            String description = editTextSalaryDescription.getText().toString();

            // insert or update
            if (userId != -1) {
                if (currentRecurringTransactionId == -1) {
                    viewModel.insertRecurringTransactions(new RecurringTransaction(userId, amount, date, description));
                } else {
                    currentRecurringTransaction.setAmount(amount);
                    currentRecurringTransaction.setDate(date);
                    currentRecurringTransaction.setDescription(description);
                    viewModel.updateRecurringTransactions(currentRecurringTransaction);
                }
            }

            // nav back
            navController.navigateUp();
        });
    }
}