package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.a3175.db.Category;
import com.example.a3175.db.Transaction;

import java.sql.Date;

public class EditTransactionFragment extends BaseFragment {

    EditText editTextAmount, editTextDate, editTextDescription, editTextCategory;
    Button buttonOK;

    Transaction currentTransaction;
    Category currentCategory;
    int currentUserId, currentTransactionId, currentCategoryId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_transaction, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // view
        editTextAmount = activity.findViewById(R.id.editTextEditTransactionAmount);
        editTextDate = activity.findViewById(R.id.editTextEditTransactionDate);
        editTextDescription = activity.findViewById(R.id.editTextEditTransactionDescription);
        editTextCategory = activity.findViewById(R.id.editTextEditTransactionCategoryName);
        buttonOK = activity.findViewById(R.id.buttonEditTransactionOK);

        // FIXME
        currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
        if (getArguments() != null) {
            currentCategoryId = getArguments().getInt("categoryId");
            currentTransactionId = getArguments().getInt("transactionId");
        }
        currentTransaction = transactionViewModel.getTransactionById(currentTransactionId);
        currentCategory = categoryViewModel.getCategoryById(currentCategoryId);

        // determine fragment purpose (add / edit)
        int previousFragmentId = navController.getPreviousBackStackEntry().getDestination().getId();
        if (previousFragmentId == R.id.categoryFragment) {
            // add transaction

            // fill editText
            if (currentCategoryId != 0) {
                editTextCategory.setText(categoryViewModel.getCategoryById(currentCategoryId).getName());
            }

            buttonOK.setOnClickListener(v -> {

                // prepare data
                double amount = Double.parseDouble(editTextAmount.getText().toString());
                amount = currentCategory.isIncome() ? amount : -amount;
                // FIXME
                Date date = null;
                String description = editTextDescription.getText().toString();

                // db insert
                transactionViewModel.insertTransactions(
                        new Transaction(currentUserId, amount, date, currentCategoryId, description)
                );

                // nav back to main *
                navController.popBackStack(R.id.mainFragment, false);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            });

        } else if (previousFragmentId == R.id.mainFragment) {
            // edit transaction

            // fill editText

            buttonOK.setOnClickListener(v -> {
                // db update


                // nav
                navController.navigateUp();
            });
        }


//        if (getArguments() != null) {
//            int previousFragmentId = navController.getPreviousBackStackEntry().getDestination().getId();
//            switch (previousFragmentId) {
//                // add transaction
//                case R.id.categoryFragment:
//                    // db insert
//                    buttonOK.setOnClickListener(v -> {
//                        int userId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
//                        int categoryId = getArguments().getInt("categoryId", -1);
////                    Category category = categoryViewModel.getCategoryById(categoryId).get(0);
//                        double amount = Double.parseDouble(editTextAmount.getText().toString());
//                        Date date = Date.valueOf(editTextDate.getText().toString());
//                        String description = editTextDescription.getText().toString();
//
//                        Transaction transaction = new Transaction(userId, amount, date, categoryId, description);
//                        transactionViewModel.insertTransactions(transaction);
//
//                        // nav
//                        navController.navigateUp();
//                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
//
//                    });
//                    break;
//                // edit transaction
//                case R.id.mainFragment:
//                    int transactionId = getArguments().getInt("transactionId", -1);
//                    Transaction transaction = transactionViewModel.getTransactionById(transactionId);
//                    break;
//            }
//        }
    }
}