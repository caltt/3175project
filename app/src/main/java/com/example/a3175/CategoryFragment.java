package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3175.db.Category;
import com.example.a3175.db.CategoryAdapter;
import com.example.a3175.db.CategoryViewModel;
import com.example.a3175.utils.AppManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryFragment extends Fragment {
    FragmentActivity activity;
    CategoryViewModel categoryViewModel;
    CategoryAdapter adapterIncomeCategories, adapterExpenseCategories;
    NavController navController;

    RecyclerView recyclerViewIncomeCategories, recyclerViewExpenseCategories;
    FloatingActionButton floatingActionButton;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        categoryViewModel = AppManager.getCategoryViewModel();
        navController = AppManager.getNavController();
        adapterExpenseCategories = new CategoryAdapter(R.layout.cell_category);
        adapterIncomeCategories = new CategoryAdapter(R.layout.cell_category);

        // setup view
        recyclerViewExpenseCategories = activity.findViewById(R.id.recyclerViewExpenseCategories);
        recyclerViewIncomeCategories = activity.findViewById(R.id.recyclerViewIncomeCategories);
        floatingActionButton = activity.findViewById(R.id.floatingActionButtonToAddCategory);

        if (getArguments() != null && getArguments().getBoolean("isAddingTransaction", false)) {
            floatingActionButton.setVisibility(View.GONE);
        }

        // recyclerView
        recyclerViewIncomeCategories.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewExpenseCategories.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewIncomeCategories.setAdapter(adapterIncomeCategories);
        recyclerViewExpenseCategories.setAdapter(adapterExpenseCategories);

        LiveData<List<Category>> incomeCategories = categoryViewModel.getAllIncomeCategories();
        incomeCategories.observe(getViewLifecycleOwner(), categories -> {
            adapterIncomeCategories.submitList(categories);
        });
        LiveData<List<Category>> expenseCategories = categoryViewModel.getAllExpenseCategories();
        expenseCategories.observe(getViewLifecycleOwner(), categories -> {
            adapterExpenseCategories.submitList(categories);
        });

        // button
        floatingActionButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_categoryFragment_to_editCategoryFragment);
        });
    }
}