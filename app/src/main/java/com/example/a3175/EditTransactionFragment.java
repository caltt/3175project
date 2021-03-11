package com.example.a3175;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.a3175.db.Category;
import com.example.a3175.db.Overview;
import com.example.a3175.db.Transaction;
import com.example.a3175.utils.Calculators;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditTransactionFragment extends BaseFragment {

    EditText editTextAmount, editTextDate, editTextDescription, editTextCategory;
    Button buttonOK;
    ImageButton buttonDatePicker;

    Transaction currentTransaction;
    Category currentCategory;
    //    Overview currentOverview;
//    int currentUserId;
    int currentTransactionId, currentCategoryId;

    LocalDate datePickerDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_transaction, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //region VIEW
        editTextAmount = activity.findViewById(R.id.editTextEditTransactionAmount);
        editTextDate = activity.findViewById(R.id.editTextEditTransactionDate);
        editTextDescription = activity.findViewById(R.id.editTextEditTransactionDescription);
        editTextCategory = activity.findViewById(R.id.editTextEditTransactionCategoryName);
        buttonDatePicker = activity.findViewById(R.id.imageButtonDatePicker);
        buttonOK = activity.findViewById(R.id.buttonEditTransactionOK);

        editTextCategory.setEnabled(false);
        editTextDate.setEnabled(false);
        buttonOK.setEnabled(!TextUtils.isEmpty(editTextAmount.getText()));
        //endregion

        //region CONTEXTUAL DATA
        // FIXME
//        currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
//        currentOverview = overviewViewModel.getByUserIdForUpdate(currentUserId);
        if (getArguments() != null) {
            currentCategoryId = getArguments().getInt("categoryId");
            currentTransactionId = getArguments().getInt("transactionId");

            // there must be a category, no matter from category id / transaction id
            // but not necessarily a transaction
            if (currentCategoryId != 0) {
                currentCategory = categoryViewModel.getById(currentCategoryId);
            } else {
                currentTransaction = transactionViewModel.getById(currentTransactionId);
                currentCategory = categoryViewModel.getById(currentTransaction.getCategoryId());
            }
        }
        //endregion

        //region DATE PICKER
        buttonDatePicker.setOnClickListener(v -> {
//            calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity);
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
//                calendar.set(year, month, dayOfMonth);
                datePickerDate = LocalDate.of(year, month + 1, dayOfMonth);
                editTextDate.setText(datePickerDate.toString());
            });
            datePickerDialog.show();
        });
        //endregion

        //region VALIDATE EDIT TEXT AND ACTIVATE BUTTON
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonOK.setEnabled(!TextUtils.isEmpty(editTextAmount.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextAmount.addTextChangedListener(textWatcher);
        //endregion

        //region DETERMINE FRAGMENT PURPOSE (ADD / EDIT)
        int previousFragmentId = navController.getPreviousBackStackEntry().getDestination().getId();
        if (previousFragmentId == R.id.categoryFragment) {
            // add transaction

            // fill editText
            if (currentCategoryId != 0) {
                editTextCategory.setText(categoryViewModel.getById(currentCategoryId).getName());
            }
            datePickerDate = LocalDate.now();
            editTextDate.setText(datePickerDate.toString());

            buttonOK.setOnClickListener(v -> {
                BigDecimal amount = new BigDecimal(editTextAmount.getText().toString());
                amount = currentCategory.isIncome() ? amount : amount.negate();
//                Date date = new Date(calendar.getTimeInMillis());
                String description = editTextDescription.getText().toString();

                // db insert
                transactionViewModel.insert(
                        new Transaction(currentUserId, amount, datePickerDate, currentCategoryId, description)
                );

                // FIXME
                // db update: overview
                Calculators.processTransaction(currentOverview, amount);
                overviewViewModel.update(currentOverview);

                // nav back to main & hide keyboard
                navController.popBackStack(R.id.mainFragment, false);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            });

        } else if (previousFragmentId == R.id.mainFragment) {
            // edit transaction

            // fill editText
//            String currentCategoryName = categoryViewModel.getCategoryById(currentTransaction.getCategoryId()).getName();
            String currentCategoryName = transactionViewModel.getCategoryById(currentTransactionId).getName();
            datePickerDate = currentTransaction.getDate();
            BigDecimal oldAmount = currentTransaction.getAmount();

            editTextCategory.setText(currentCategoryName);
            editTextAmount.setText(oldAmount.abs().toString());
            editTextDate.setText(datePickerDate.toString());
//            calendar.setTime(datetime);
            editTextDescription.setText(currentTransaction.getDescription());

            buttonOK.setOnClickListener(v -> {
                // db update
                BigDecimal amount = new BigDecimal(editTextAmount.getText().toString());
                amount = currentCategory.isIncome() ? amount : amount.negate();
//                Date date = new Date(calendar.getTimeInMillis());
                String description = editTextDescription.getText().toString();

                currentTransaction.setAmount(amount);
                currentTransaction.setDate(datePickerDate);
                currentTransaction.setDescription(description);

                transactionViewModel.update(currentTransaction);

                // update overview
                Calculators.processTransaction(currentOverview, amount.subtract(oldAmount));
                overviewViewModel.update(currentOverview);

                // nav back & hide keyboard
                navController.navigateUp();
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            });
        }
        //endregion
    }

}