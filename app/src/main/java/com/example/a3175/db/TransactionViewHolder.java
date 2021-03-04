package com.example.a3175.db;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.R;

public class TransactionViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTransactionCategory,
            textViewTransactionDescription,
            textViewTransactionAmount;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTransactionCategory = itemView.findViewById(R.id.textViewTransactionCategory);
        textViewTransactionDescription = itemView.findViewById(R.id.textViewTransactionDescription);
        textViewTransactionAmount = itemView.findViewById(R.id.textViewTransactionAmount);
    }
}
