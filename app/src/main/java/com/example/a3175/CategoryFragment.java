package com.example.a3175;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3175.db.Category;
import com.example.a3175.db.CategoryAdapter;
import com.example.a3175.db.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryFragment extends BaseFragment {

    CategoryAdapter adapterIncomeCategories, adapterExpenseCategories,
            adapterIncomeForTransaction, adapterExpenseForTransaction;
    RecyclerView recyclerViewIncomeCategories, recyclerViewExpenseCategories;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // adapter
        adapterIncomeForTransaction = new CategoryAdapter(activity, R.layout.cell_category, true);
        adapterExpenseForTransaction = new CategoryAdapter(activity, R.layout.cell_category, true);
        adapterExpenseCategories = new CategoryAdapter(activity, R.layout.cell_category, false);
        adapterIncomeCategories = new CategoryAdapter(activity, R.layout.cell_category, false);

        // setup view
        recyclerViewExpenseCategories = activity.findViewById(R.id.recyclerViewExpenseCategories);
        recyclerViewIncomeCategories = activity.findViewById(R.id.recyclerViewIncomeCategories);
        floatingActionButton = activity.findViewById(R.id.floatingActionButtonToAddCategory);

        // recyclerView
        recyclerViewIncomeCategories.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewExpenseCategories.setLayoutManager(new LinearLayoutManager(activity));


        // determine fragment purpose
        // argument passed in:  add transaction
        // no argument:         edit category
        LiveData<List<Category>> incomeCategories = categoryViewModel.getAllIncomeCategories();
        LiveData<List<Category>> expenseCategories = categoryViewModel.getAllExpenseCategories();

        if (getArguments() != null && getArguments().getBoolean("isAddingTransaction", false)) {
            // for adding transaction

            // view
            floatingActionButton.setVisibility(View.GONE);
            recyclerViewIncomeCategories.setAdapter(adapterIncomeForTransaction);
            recyclerViewExpenseCategories.setAdapter(adapterExpenseForTransaction);

            // live data
            incomeCategories.observe(getViewLifecycleOwner(), categories -> {
                adapterIncomeForTransaction.submitList(categories);
            });
            expenseCategories.observe(getViewLifecycleOwner(), categories -> {
                adapterExpenseForTransaction.submitList(categories);
            });

        } else {
            // for edit

            // view
            recyclerViewIncomeCategories.setAdapter(adapterIncomeCategories);
            recyclerViewExpenseCategories.setAdapter(adapterExpenseCategories);

            // live data
            incomeCategories.observe(getViewLifecycleOwner(), categories -> {
                adapterIncomeCategories.submitList(categories);
            });
            expenseCategories.observe(getViewLifecycleOwner(), categories -> {
                adapterExpenseCategories.submitList(categories);
            });

            // swipe delete
        }



        // button
        floatingActionButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_categoryFragment_to_editCategoryFragment);
        });
    }
}