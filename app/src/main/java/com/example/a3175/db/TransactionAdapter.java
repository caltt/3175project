package com.example.a3175.db;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class TransactionAdapter extends ListAdapter<Transaction, TransactionViewHolder> {
    private int layoutId;

    public TransactionAdapter(int layoutId) {
        super(new DiffUtil.ItemCallback<Transaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return oldItem.getUserId() == newItem.getUserId()
                        && oldItem.getAmount() == newItem.getAmount()
                        && oldItem.getCategoryId() == newItem.getCategoryId()
                        && oldItem.getDatetime().equals(newItem.getDatetime())
                        && oldItem.getDescription().equals(newItem.getDescription());
            }
        });
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

    }
}
