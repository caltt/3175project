package com.example.a3175.db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class RecurringTransactionAdapter extends ListAdapter<RecurringTransaction, RecurringTransactionViewHolder> {
    //    private RecurringTransactionViewModel viewModel;
    private int layoutId;

    public RecurringTransactionAdapter(int layoutId) {
        super(new DiffUtil.ItemCallback<RecurringTransaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull RecurringTransaction oldItem, @NonNull RecurringTransaction newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull RecurringTransaction oldItem, @NonNull RecurringTransaction newItem) {
                return oldItem.getUserId() == newItem.getUserId()
                        && oldItem.getAmount() == newItem.getAmount()
                        && oldItem.getDate() == newItem.getDate()
                        && oldItem.getDescription().equals(newItem.getDescription());
            }
        });
//        this.viewModel = viewModel;
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
        RecurringTransaction transaction = getItem(position);

        holder.textViewRecurringTransactionId.setText(String.valueOf(position + 1));
        holder.textViewRecurringTransactionDate.setText(String.valueOf(transaction.getDate()));
        holder.textViewRecurringTransactionDescription.setText(transaction.getDescription());
        holder.textViewRecurringTransactionAmount.setText(String.valueOf(transaction.getAmount()));
    }
}
