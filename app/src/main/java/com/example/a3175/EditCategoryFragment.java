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

import com.example.a3175.db.Category;

public class EditCategoryFragment extends BaseFragment {

    RadioButton radioButtonIsIncome, radioButtonIsExpense;
    EditText editTextCategoryName;
    Button buttonOK;

    Category currentCategory;
    int currentCategoryId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // view
        radioButtonIsIncome = activity.findViewById(R.id.radioButtonEditCategoryIsIncome);
        radioButtonIsExpense = activity.findViewById(R.id.radioButtonEditCategoryIsExpense);
        editTextCategoryName = activity.findViewById(R.id.editTextEditCategoryName);
        buttonOK = activity.findViewById(R.id.buttonEditCategoryOK);

        radioButtonIsExpense.setChecked(true);

        // validate input & activate ok button
        buttonOK.setEnabled(false);

        View.OnClickListener onClickListener = v -> {
            buttonOK.setEnabled(!editTextCategoryName.getText().toString().isEmpty());
        };
        radioButtonIsIncome.setOnClickListener(onClickListener);
        radioButtonIsExpense.setOnClickListener(onClickListener);

        editTextCategoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonOK.setEnabled(!editTextCategoryName.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // determine fragment purpose (create / edit)
        currentCategoryId = -1;     // a default value for create
        if (getArguments() != null) {
            currentCategoryId = getArguments().getInt("categoryId", -1);
        }

        if (currentCategoryId == -1) {
            // create
            buttonOK.setOnClickListener(v -> {
                categoryViewModel.insertCategories(
                        new Category(editTextCategoryName.getText().toString(), radioButtonIsIncome.isChecked()));
                navController.navigateUp();

            });
        } else {
            // edit

            // fill editText with data
            currentCategory = categoryViewModel.getCategoryById(currentCategoryId);
            if (currentCategory.isIncome()) {
                radioButtonIsIncome.setChecked(true);
            } else {
                radioButtonIsExpense.setChecked(true);
            }
            editTextCategoryName.setText(currentCategory.getName());

            // button function
            buttonOK.setOnClickListener(v -> {
                currentCategory.setName(editTextCategoryName.getText().toString());
                currentCategory.setIncome(radioButtonIsIncome.isChecked());
                categoryViewModel.updateCategories(currentCategory);
                navController.navigateUp();

            });
        }



        // button: insert / update
//        buttonEditCategory.setOnClickListener(v -> {
//            if (currentCategoryId == -1) {
//                // db insert
//                categoryViewModel.insertCategories(new Category(editTextCategoryName.getText().toString()));
//            } else {
//                // db update
//                currentCategory.setName(editTextCategoryName.getText().toString());
//                currentCategory.setIncome(radioButtonIsIncome.isChecked());
//                categoryViewModel.updateCategories(currentCategory);
//            }
//
//            // nav back
//            navController.navigateUp();
//        });

    }
}