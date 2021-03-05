package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.a3175.db.BigExpenseViewModel;
import com.example.a3175.db.CategoryViewModel;
import com.example.a3175.db.OverviewViewModel;
import com.example.a3175.db.RecurringTransactionViewModel;
import com.example.a3175.db.TransactionViewModel;
import com.example.a3175.db.UserViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BaseFragment extends Fragment {
    protected static String TAG = "test";

    protected static FragmentActivity activity;
    protected static SharedPreferences preferences;
    protected static SharedPreferences.Editor editor;
    protected static NavController navController;
    protected static NavController navControllerBottomNav;
    protected static InputMethodManager inputMethodManager;

    protected static UserViewModel userViewModel;
    protected static OverviewViewModel overviewViewModel;
    protected static CategoryViewModel categoryViewModel;
    protected static TransactionViewModel transactionViewModel;
    protected static RecurringTransactionViewModel recurringTransactionViewModel;
    protected static BigExpenseViewModel bigExpenseViewModel;

    protected static DateFormat dateFormat;
    protected static Calendar calendar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        navController = Navigation.findNavController(activity, R.id.navHostFragmentMainActivity);
//        navControllerBottomNav = Navigation.findNavController(activity, R.id.navHostFragmentMainFragmentBottom);
        inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        userViewModel = new ViewModelProvider(activity).get(UserViewModel.class);
        overviewViewModel = new ViewModelProvider(activity).get(OverviewViewModel.class);
        categoryViewModel = new ViewModelProvider(activity).get(CategoryViewModel.class);
        transactionViewModel = new ViewModelProvider(activity).get(TransactionViewModel.class);
        recurringTransactionViewModel = new ViewModelProvider(activity).get(RecurringTransactionViewModel.class);
        bigExpenseViewModel = new ViewModelProvider(activity).get(BigExpenseViewModel.class);

         calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    }

    // TODO
    public <T> ItemTouchHelper getSwipeDeleteItemTouchHelper(LiveData<List<T>> liveData){
        return null;
    }

}
