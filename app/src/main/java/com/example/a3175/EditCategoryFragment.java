package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.a3175.db.Category;
import com.example.a3175.db.CategoryViewModel;
import com.example.a3175.utils.AppManager;

public class EditCategoryFragment extends Fragment {
    FragmentActivity activity;
    CategoryViewModel categoryViewModel;
    NavController navController;

    RadioButton radioButtonIsIncome, radioButtonIsExpense;
    EditText editTextCategoryName;
    Button buttonEditCategory;

    Category currentCategory;
    int currentCategoryId;

    public EditCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = requireActivity();
        categoryViewModel = AppManager.getCategoryViewModel();
        navController = AppManager.getNavController();

        // view
        radioButtonIsIncome = activity.findViewById(R.id.radioButtonIsCategoryIncome);
        radioButtonIsExpense = activity.findViewById(R.id.radioButtonIsCategoryExpense);
        editTextCategoryName = activity.findViewById(R.id.editTextCategoryName);
        buttonEditCategory = activity.findViewById(R.id.buttonAddCategory);

        // fill editText with data
        currentCategoryId = -1;     // a default value for create
        if (getArguments() != null) {
            currentCategoryId = getArguments().getInt("categoryId", -1);
            if (currentCategoryId != -1) {
                currentCategory = categoryViewModel.getCategoryById(currentCategoryId).get(0);
                if (currentCategory.isIncome()) {
                    radioButtonIsIncome.setChecked(true);
                } else {
                    radioButtonIsExpense.setChecked(true);
                }
                editTextCategoryName.setText(currentCategory.getName());
            }
        }

        // activate ok button when data valid
        buttonEditCategory.setEnabled(false);


        editTextCategoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonEditCategory.setEnabled(!editTextCategoryName.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // button: insert / update
        buttonEditCategory.setOnClickListener(v -> {
            if (currentCategoryId == -1) {
                // db insert
                categoryViewModel.insertCategories(new Category(editTextCategoryName.getText().toString()));
            } else {
                // db update
                currentCategory.setName(editTextCategoryName.getText().toString());
                currentCategory.setIncome(radioButtonIsIncome.isChecked());
                categoryViewModel.updateCategories(currentCategory);
            }

            // nav back
            navController.navigateUp();
        });
    }
}