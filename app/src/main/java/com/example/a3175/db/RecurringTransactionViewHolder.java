package com.example.a3175.db;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.R;

public class RecurringTransactionViewHolder extends RecyclerView.ViewHolder {
    TextView textViewRecurringTransactionId,
            textViewRecurringTransactionDate,
            textViewRecurringTransactionAmount,
            textViewRecurringTransactionDescription;


    public RecurringTransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewRecurringTransactionId = itemView.findViewById(R.id.textViewRecurringTransactionId);
        textViewRecurringTransactionDate = itemView.findViewById(R.id.textViewRecurringTransactionDate);
        textViewRecurringTransactionAmount = itemView.findViewById(R.id.textViewRecurringTransactionAmount);
        textViewRecurringTransactionDescription = itemView.findViewById(R.id.textViewRecurringTransactionDescription);
    }
}
