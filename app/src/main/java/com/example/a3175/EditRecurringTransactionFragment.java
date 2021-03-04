package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    int currentUserId, currentRecurringTransactionId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_recurring_transaction, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get user id
        currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
        if (currentUserId == -1) {
            currentUserId = preferences.getInt(getResources().getString(R.string.logging_in_user_id), -1);
        }

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
        currentRecurringTransactionId = getArguments() == null
                ? -1   // create
                : getArguments().getInt("recurringTransactionId", -1);   // edit

        if (currentRecurringTransactionId == -1) {

            // create
            buttonOK.setOnClickListener(v -> {
                double amount = Double.parseDouble(editTextSalaryAmount.getText().toString());
                amount = radioButtonIsBill.isChecked() ? -amount : amount;
                int date = Integer.parseInt(editTextSalaryDate.getText().toString());
                String description = editTextSalaryDescription.getText().toString();

                // db insert
                recurringTransactionViewModel.insertRecurringTransactions(
                        new RecurringTransaction(currentUserId, amount, date, description));

                //nav back
                navController.navigateUp();
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

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
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            });

        }
    }
}