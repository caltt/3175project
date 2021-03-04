package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a3175.db.Overview;
import com.example.a3175.db.OverviewViewModel;
import com.example.a3175.db.Transaction;
import com.example.a3175.db.TransactionAdapter;
import com.example.a3175.db.TransactionViewModel;

import java.util.List;

public class ExpenseTrackerFragment extends BaseFragment {

    TransactionAdapter adapter;

    RecyclerView recyclerView;
    TextView textViewToday, textViewSavings, textViewIncome, textViewDebt;

    int currentUserId;

    public ExpenseTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_tracker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);

        // setup
        adapter = new TransactionAdapter(categoryViewModel, navController, R.layout.cell_transaction);


        // view
        textViewToday = activity.findViewById(R.id.textViewAvailableTodayAmount);
        textViewSavings = activity.findViewById(R.id.textViewSavingsAmount);
        textViewIncome = activity.findViewById(R.id.textViewIncomeAmount);
        textViewDebt = activity.findViewById(R.id.textViewDebtAmount);
        recyclerView = activity.findViewById(R.id.recyclerViewTransaction);

        // overview
        LiveData<Overview> liveDataOverview = overviewViewModel.getOverviewByUserId(currentUserId);
        liveDataOverview.observe(getViewLifecycleOwner(), overview -> {
                textViewSavings.setText(String.valueOf(overview.getSavings()));
                textViewIncome.setText(String.valueOf(overview.getIncomes()));
                textViewDebt.setText(String.valueOf(overview.getDebt()));
        });

        // transaction
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);

        LiveData<List<Transaction>> liveDataTransaction = transactionViewModel.getTransactionsByUserId(currentUserId);
        liveDataTransaction.observe(getViewLifecycleOwner(),
                transactions -> adapter.submitList(liveDataTransaction.getValue()));

    }
}