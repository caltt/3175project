package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        editTextCategory = activity.findViewById(R.id.editTextEditTransactionCategory);
        buttonOK = activity.findViewById(R.id.buttonEditTransactionOK);

        if (getArguments() != null) {
            int previousFragmentId = navController.getPreviousBackStackEntry().getDestination().getId();
            switch (previousFragmentId) {
                // add transaction
                case R.id.categoryFragment:
                    // db insert
                    buttonOK.setOnClickListener(v -> {
                    int userId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
                        int categoryId = getArguments().getInt("categoryId", -1);
//                    Category category = categoryViewModel.getCategoryById(categoryId).get(0);
                        double amount = Double.parseDouble(editTextAmount.getText().toString());
                        Date date = Date.valueOf(editTextDate.getText().toString());
                        String description = editTextDescription.getText().toString();

                        Transaction transaction = new Transaction(userId, amount, date, categoryId, description);
                        transactionViewModel.insertTransactions(transaction);

                        // nav
                        navController.navigateUp();

                    });
                    break;
                // edit transaction
                case R.id.mainFragment:
                    int transactionId = getArguments().getInt("transactionId", -1);
                    Transaction transaction = transactionViewModel.getTransactionById(transactionId);
                    break;
            }
        }
    }
}