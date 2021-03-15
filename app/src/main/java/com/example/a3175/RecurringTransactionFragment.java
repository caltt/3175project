package com.example.a3175;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.db.RecurringTransaction;
import com.example.a3175.db.RecurringTransactionAdapter;

import java.util.List;

public class RecurringTransactionFragment extends BaseFragment {
    RecyclerView recyclerViewSalary, recyclerViewBill;
    RecurringTransactionAdapter adapterForSalary, adapterForBill;

    Button buttonToAdd;

    public RecurringTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recurring_transaction, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        adapterForSalary = new RecurringTransactionAdapter(activity, R.layout.cell_recurring_transaction);
        adapterForBill = new RecurringTransactionAdapter(activity, R.layout.cell_recurring_transaction);

        // setup view
        recyclerViewSalary = activity.findViewById(R.id.recyclerViewSalary);
        recyclerViewBill = activity.findViewById(R.id.recycleViewBill);

        // recycle view
        recyclerViewSalary.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewBill.setLayoutManager(new LinearLayoutManager(activity));
        buttonToAdd = activity.findViewById(R.id.buttonManageSalaryAdd);

        recyclerViewSalary.setAdapter(adapterForSalary);
        recyclerViewBill.setAdapter(adapterForBill);

        LiveData<List<RecurringTransaction>> liveDataSalary = recurringTransactionViewModel.getRecurringIncomesByUserId(currentUserId);
        LiveData<List<RecurringTransaction>> liveDataBill = recurringTransactionViewModel.getRecurringExpensesByUserId(currentUserId);
        liveDataSalary.observe(getViewLifecycleOwner(), recurringTransactions -> {
            adapterForSalary.submitList(liveDataSalary.getValue());
        });
        liveDataBill.observe(getViewLifecycleOwner(), recurringTransactions -> {
            adapterForBill.submitList(liveDataBill.getValue());
        });


        // button
        buttonToAdd.setOnClickListener(v -> {
            navController.navigate(R.id.action_recurringTransactionFragment_to_editRecurringTransactionFragment);
        });
    }
}