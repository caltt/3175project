package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a3175.db.BigExpenseViewModel;
import com.example.a3175.db.CategoryViewModel;
import com.example.a3175.db.OverviewViewModel;
import com.example.a3175.db.RecurringTransactionViewModel;
import com.example.a3175.db.TransactionViewModel;
import com.example.a3175.db.UserViewModel;

public class BaseFragment extends Fragment {
    protected static FragmentActivity activity;
    protected static SharedPreferences preferences;
    protected static SharedPreferences.Editor editor;
    protected static NavController navController;

    protected static UserViewModel userViewModel;
    protected static OverviewViewModel overviewViewModel;
    protected static CategoryViewModel categoryViewModel;
    protected static TransactionViewModel transactionViewModel;
    protected static RecurringTransactionViewModel recurringTransactionViewModel;
    protected static BigExpenseViewModel bigExpenseViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        navController = Navigation.findNavController(activity, R.id.navHostFragment);

        userViewModel = new ViewModelProvider(activity).get(UserViewModel.class);
        overviewViewModel = new ViewModelProvider(activity).get(OverviewViewModel.class);
        categoryViewModel = new ViewModelProvider(activity).get(CategoryViewModel.class);
        transactionViewModel = new ViewModelProvider(activity).get(TransactionViewModel.class);
        recurringTransactionViewModel = new ViewModelProvider(activity).get(RecurringTransactionViewModel.class);
        bigExpenseViewModel = new ViewModelProvider(activity).get(BigExpenseViewModel.class);
    }
}
