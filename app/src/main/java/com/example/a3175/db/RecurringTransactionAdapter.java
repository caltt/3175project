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

import java.math.BigDecimal;

public class RecurringTransactionAdapter extends ListAdapter<RecurringTransaction, RecurringTransactionViewHolder> {
    NavController navController;
    private final int layoutId;

    public RecurringTransactionAdapter(Activity activity, int layoutId) {
        super(new DiffUtil.ItemCallback<RecurringTransaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull RecurringTransaction oldItem, @NonNull RecurringTransaction newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull RecurringTransaction oldItem, @NonNull RecurringTransaction newItem) {
                return oldItem.getUserId() == newItem.getUserId()
                        && oldItem.getAmount().equals(newItem.getAmount())
                        && oldItem.getDate() == newItem.getDate()
                        && oldItem.getDescription().equals(newItem.getDescription());
            }
        });
        this.navController = Navigation.findNavController(activity, R.id.navHostFragmentMainActivity);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public RecurringTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // itemView
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutId, parent, false);

        // viewHolder
        return new RecurringTransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecurringTransactionViewHolder holder, int position) {
        RecurringTransaction recurringTransaction = getItem(position);

        holder.textViewRecurringTransactionId.setText(String.valueOf(position + 1));
        holder.textViewRecurringTransactionDate.setText(String.valueOf(recurringTransaction.getDate()));
        holder.textViewRecurringTransactionDescription.setText(recurringTransaction.getDescription());
        holder.textViewRecurringTransactionAmount.setText(
                recurringTransaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("recurringTransactionId", recurringTransaction.getId());
            navController.navigate(R.id.action_recurringTransactionFragment_to_editRecurringTransactionFragment, bundle);
        });
    }
}
