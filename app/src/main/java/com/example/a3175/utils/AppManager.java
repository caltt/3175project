package com.example.a3175.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a3175.R;
import com.example.a3175.db.BigExpenseViewModel;
import com.example.a3175.db.CategoryViewModel;
import com.example.a3175.db.OverviewViewModel;
import com.example.a3175.db.RecurringTransactionViewModel;
import com.example.a3175.db.TransactionViewModel;
import com.example.a3175.db.UserViewModel;

public class AppManager {
    private static SharedPreferences preferences;
    private static NavController navController;

    private static UserViewModel userViewModel;
    private static OverviewViewModel overviewViewModel;
    private static CategoryViewModel categoryViewModel;
    private static TransactionViewModel transactionViewModel;
    private static RecurringTransactionViewModel recurringTransactionViewModel;
    private static BigExpenseViewModel bigExpenseViewModel;

    public AppManager(AppCompatActivity activity) {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        navController = Navigation.findNavController(activity, R.id.navHostFragment);

        userViewModel = new ViewModelProvider(activity).get(UserViewModel.class);
        overviewViewModel = new ViewModelProvider(activity).get(OverviewViewModel.class);
        categoryViewModel = new ViewModelProvider(activity).get(CategoryViewModel.class);
        transactionViewModel = new ViewModelProvider(activity).get(TransactionViewModel.class);
        recurringTransactionViewModel = new ViewModelProvider(activity).get(RecurringTransactionViewModel.class);
        bigExpenseViewModel = new ViewModelProvider(activity).get(BigExpenseViewModel.class);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static NavController getNavController() {
        return navController;
    }

    public static UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public static OverviewViewModel getOverviewViewModel() {
        return overviewViewModel;
    }

    public static CategoryViewModel getCategoryViewModel() {
        return categoryViewModel;
    }

    public static TransactionViewModel getTransactionViewModel() {
        return transactionViewModel;
    }

    public static RecurringTransactionViewModel getRecurringTransactionViewModel() {
        return recurringTransactionViewModel;
    }

    public static BigExpenseViewModel getBigExpenseViewModel() {
        return bigExpenseViewModel;
    }
}
