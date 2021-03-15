package com.example.a3175;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class EditBigExpenseFragment extends BaseFragment {

    EditText editTextAmount, editTextDescription;
    Button buttonOK;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_big_expense, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        editTextAmount = activity.findViewById(R.id.editTextEditBigExpenseAmount);
        editTextDescription = activity.findViewById(R.id.editTextEditBigExpenseDescription);
        buttonOK = activity.findViewById(R.id.buttonEditBigExpenseOK);

        buttonOK.setOnClickListener(v -> {
            navController.navigate(R.id.action_editBigExpenseFragment_to_bigExpensePlanFragment);
        });

    }
}