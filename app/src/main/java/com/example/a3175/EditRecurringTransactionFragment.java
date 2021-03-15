package com.example.a3175;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.a3175.db.RecurringTransaction;
import com.example.a3175.db.Transaction;
import com.example.a3175.utils.Calculators;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditRecurringTransactionFragment extends BaseFragment {

    EditText editTextSalaryAmount, editTextSalaryDate, editTextSalaryDescription;
    Button buttonOK;
    RadioButton radioButtonIsSalary, radioButtonIsBill;

    RecurringTransaction currentRecurringTransaction;
    //    Overview currentOverview;
//    int currentUserId;
    int currentRecurringTransactionId;

    int categoryId, date;
    BigDecimal amount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_recurring_transaction, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //region VIEW
        editTextSalaryAmount = activity.findViewById(R.id.editTextEditRecurringTransactionAmount);
        editTextSalaryDate = activity.findViewById(R.id.editTextEditRecurringTransactionDate);
        editTextSalaryDescription = activity.findViewById(R.id.editTextEditRecurringTransactionDescription);
        buttonOK = activity.findViewById(R.id.buttonEditRecurringTransactionOK);
        radioButtonIsSalary = activity.findViewById(R.id.radioButtonIsSalary);
        radioButtonIsBill = activity.findViewById(R.id.radioButtonIsBill);

        radioButtonIsSalary.setChecked(true);
        buttonOK.setEnabled(false);
        //endregion

        //region VALIDATE EDIT TEXT & ACTIVATE BUTTON
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
        //endregion

        //region DETERMINE FRAGMENT PURPOSE (ADD / EDIT)
        currentRecurringTransactionId = getArguments() == null
                ? -1   // create
                : getArguments().getInt("recurringTransactionId", -1);   // edit

        if (currentRecurringTransactionId == -1) {

            // add a recurring transaction
            buttonOK.setOnClickListener(v -> {
                amount = new BigDecimal(editTextSalaryAmount.getText().toString());
                categoryId = 0;
                if (radioButtonIsBill.isChecked()) {
                    amount = amount.negate();
                    categoryId = categoryViewModel.getByName(getResources().getString(R.string.category_bill)).getId();
                } else {
                    categoryId = categoryViewModel.getByName(getResources().getString(R.string.category_salary)).getId();
                }
                date = Integer.parseInt(editTextSalaryDate.getText().toString());
                String description = editTextSalaryDescription.getText().toString();

                // db insert
                recurringTransactionViewModel.insert(
                        new RecurringTransaction(currentUserId, categoryId, amount, date, description));

                // !!!
                // if the adding date is exactly the transaction date, ask whether do this transaction immediately
                if (date == LocalDate.now().getDayOfMonth()) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Do this transaction now?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                // db insert: transaction
                                transactionViewModel.insert(
                                        new Transaction(currentUserId, amount, LocalDate.now(), categoryId, description));

                                Log.d(TAG, "onActivityCreated: " + amount);

                                // db update: overview
                                Calculators.processRecurringTransaction(currentOverview, amount);
                                overviewViewModel.update(currentOverview);
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                            })
                            .create()
                            .show();
                }


                //nav back
                navController.navigateUp();
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

            });

        } else {
            // edit a recurring transaction

            currentRecurringTransaction = recurringTransactionViewModel.getById(currentRecurringTransactionId);

            // fill editText with data
            BigDecimal amount = currentRecurringTransaction.getAmount();
            if (amount.compareTo(BigDecimal.ZERO) < 0) {    // < 0
                radioButtonIsBill.setChecked(true);
            } else {
                radioButtonIsSalary.setChecked(true);
            }
            editTextSalaryAmount.setText(amount.abs().toPlainString());
            editTextSalaryDate.setText(String.valueOf(currentRecurringTransaction.getDate()));
            editTextSalaryDescription.setText(String.valueOf(currentRecurringTransaction.getDescription()));

            buttonOK.setOnClickListener(v -> {
                // db update
                BigDecimal newAmount = new BigDecimal(editTextSalaryAmount.getText().toString());
                newAmount = radioButtonIsBill.isChecked() ? newAmount.negate() : newAmount;
                int date = Integer.parseInt(editTextSalaryDate.getText().toString());
                String description = editTextSalaryDescription.getText().toString();

                currentRecurringTransaction.setAmount(newAmount);
                currentRecurringTransaction.setDate(date);
                currentRecurringTransaction.setDescription(description);
                recurringTransactionViewModel.update(currentRecurringTransaction);

                // nav back
                navController.navigateUp();
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            });

        }
        //endregion
    }
}