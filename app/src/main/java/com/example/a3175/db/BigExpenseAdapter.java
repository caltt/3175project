package com.example.a3175.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.a3175.R;
import com.example.a3175.utils.Utils;

import java.math.BigDecimal;

public class BigExpenseAdapter extends ListAdapter<BigExpense, BigExpenseViewHolder> {
    BigExpenseViewModel bigExpenseViewModel;
    NavController navController;
    int layoutId;

    public BigExpenseAdapter(Activity activity, BigExpenseViewModel bigExpenseViewModel, int layoutId) {
        super(new DiffUtil.ItemCallback<BigExpense>() {
            @Override
            public boolean areItemsTheSame(@NonNull BigExpense oldItem, @NonNull BigExpense newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull BigExpense oldItem, @NonNull BigExpense newItem) {
                return oldItem.getAmount().equals(newItem.getAmount())
                        && oldItem.getDatetime().equals(newItem.getDatetime())
                        && oldItem.getDescription().equals(newItem.getDescription())
                        && oldItem.getIncomeNeeded().equals(newItem.getIncomeNeeded())
                        && oldItem.getSavingNeeded().equals(newItem.getSavingNeeded())
                        && oldItem.getLoanNeeded().equals(newItem.getLoanNeeded());
            }
        });

        this.navController = Navigation.findNavController(activity, R.id.navHostFragmentMainActivity);
        this.bigExpenseViewModel = bigExpenseViewModel;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public BigExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // itemView
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutId, parent, false);

        // viewHolder
        return new BigExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BigExpenseViewHolder holder, int position) {
        BigExpense bigExpense = getItem(position);

        holder.textViewIncomeAmount.setText(Utils.formatBigDecimal(bigExpense.getIncomeNeeded()));
        holder.textViewSavingsAmount.setText(Utils.formatBigDecimal(bigExpense.getSavingNeeded()));
        holder.textViewLoanAmount.setText(Utils.formatBigDecimal(bigExpense.getLoanNeeded()));

        // db insert: big expense

        // db update: overview

        // nav back
        holder.itemView.setOnClickListener(v -> {
            navController.popBackStack(R.id.mainFragment, false);
        });
    }
}
