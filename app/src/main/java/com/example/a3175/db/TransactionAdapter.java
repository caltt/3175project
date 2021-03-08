package com.example.a3175.db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.a3175.R;

public class TransactionAdapter extends ListAdapter<Transaction, TransactionViewHolder> {
    CategoryViewModel categoryViewModel;
    NavController navController;

    private int layoutId;

    public TransactionAdapter(CategoryViewModel categoryViewModel, NavController navController, int layoutId) {
        super(new DiffUtil.ItemCallback<Transaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return oldItem.getUserId() == newItem.getUserId()
                        && Math.abs(oldItem.getAmount() - newItem.getAmount()) < 0.01
                        && oldItem.getCategoryId() == newItem.getCategoryId()
                        && oldItem.getDate().equals(newItem.getDate())
                        && oldItem.getDescription().equals(newItem.getDescription());
            }
        });

        this.categoryViewModel = categoryViewModel;
        this.navController = navController;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // itemView
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutId, parent, false);

        // viewHolder
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = getItem(position);

        // FIXME
        holder.textViewTransactionCategory.setText(categoryViewModel.getById(transaction.getCategoryId()).getName());
        holder.textViewTransactionDescription.setText(transaction.getDescription());
        holder.textViewTransactionAmount.setText(String.valueOf(transaction.getAmount()));

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("transactionId", transaction.getId());
            navController.navigate(R.id.action_mainFragment_to_editTransactionFragment, bundle);
        });
    }
}
