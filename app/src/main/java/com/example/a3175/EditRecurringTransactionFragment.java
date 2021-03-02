package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.a3175.db.RecurringTransaction;

public class EditRecurringTransactionFragment extends BaseFragment {

    EditText editTextSalaryAmount, editTextSalaryDate, editTextSalaryDescription;
    Button buttonOK;
    RadioButton radioButtonIsSalary, radioButtonIsBill;

    RecurringTransaction currentRecurringTransaction;
    int currentRecurringTransactionId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_recurring_transaction, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup view
        editTextSalaryAmount = activity.findViewById(R.id.editTextEditRecurringTransactionAmount);
        editTextSalaryDate = activity.findViewById(R.id.editTextEditRecurringTransactionDate);
        editTextSalaryDescription = activity.findViewById(R.id.editTextEditRecurringTransactionDescription);
        buttonOK = activity.findViewById(R.id.buttonEditRecurringTransactionOK);
        radioButtonIsSalary = activity.findViewById(R.id.radioButtonIsSalary);
        radioButtonIsBill = activity.findViewById(R.id.radioButtonIsBill);

        radioButtonIsSalary.setChecked(true);
        buttonOK.setEnabled(false);

        // activate button ok if editText all valid
        TextWatcher textWatcher = new TextWatcher() {
            String amount, date;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amount = editTextSalaryAmount.getText().toString();
                date = editTextSalaryDate.getText().toString();
                buttonOK.setEnabled(!amount.isEmpty() && !date.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        editTextSalaryAmount.addTextChangedListener(textWatcher);
        editTextSalaryDate.addTextChangedListener(textWatcher);


        // determine fragment purpose (create / edit)
        currentRecurringTransactionId = -1; // default for create
        if (getArguments() != null) {
            currentRecurringTransactionId = getArguments().getInt("recurringTransactionId", -1);
        }

        if (currentRecurringTransactionId == -1) {

            // create
            buttonOK.setOnClickListener(v -> {
                int userId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
                double amount = Double.parseDouble(editTextSalaryAmount.getText().toString());
                amount = radioButtonIsBill.isChecked() ? -amount : amount;
                int date = Integer.parseInt(editTextSalaryDate.getText().toString());
                String description = editTextSalaryDescription.getText().toString();

                // db insert
                recurringTransactionViewModel.insertRecurringTransactions(
                        new RecurringTransaction(userId, amount, date, description));

                //nav back
                navController.navigateUp();
            });

        } else {
            // edit

            currentRecurringTransaction = recurringTransactionViewModel.getRecurringTransactionById(currentRecurringTransactionId);

            // fill editText with data
            double amount = currentRecurringTransaction.getAmount();
            if (amount < 0) {
                radioButtonIsBill.setChecked(true);
            } else {
                radioButtonIsSalary.setChecked(true);
            }
            editTextSalaryAmount.setText(String.valueOf(Math.abs(amount)));
            editTextSalaryDate.setText(String.valueOf(currentRecurringTransaction.getDate()));
            editTextSalaryDescription.setText(String.valueOf(currentRecurringTransaction.getDescription()));

            buttonOK.setOnClickListener(v -> {
                // db update
                double newAmount = Double.parseDouble(editTextSalaryAmount.getText().toString());
                newAmount = radioButtonIsBill.isChecked() ? -newAmount : newAmount;
                int date = Integer.parseInt(editTextSalaryDate.getText().toString());
                String description = editTextSalaryDescription.getText().toString();

                currentRecurringTransaction.setAmount(newAmount);
                currentRecurringTransaction.setDate(date);
                currentRecurringTransaction.setDescription(description);
                recurringTransactionViewModel.updateRecurringTransactions(currentRecurringTransaction);

                // nav back
                navController.navigateUp();

            });

        }

        // button
        buttonOK.setOnClickListener(v -> {
            int userId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
            double amount = Double.parseDouble(editTextSalaryAmount.getText().toString());
            amount = radioButtonIsBill.isChecked() ? -amount : amount;
            int date = Integer.parseInt(editTextSalaryDate.getText().toString());
            String description = editTextSalaryDescription.getText().toString();

            // insert or update
            if (userId != -1) {
                if (currentRecurringTransactionId == -1) {
                    recurringTransactionViewModel.insertRecurringTransactions(new RecurringTransaction(userId, amount, date, description));
                } else {
                    currentRecurringTransaction.setAmount(amount);
                    currentRecurringTransaction.setDate(date);
                    currentRecurringTransaction.setDescription(description);
                    recurringTransactionViewModel.updateRecurringTransactions(currentRecurringTransaction);
                }
            }

            // nav back
            navController.navigateUp();
        });
    }
}