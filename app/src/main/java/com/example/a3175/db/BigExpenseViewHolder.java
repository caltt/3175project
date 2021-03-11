package com.example.a3175.db;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.R;

public class BigExpenseViewHolder extends RecyclerView.ViewHolder {
    TextView textViewIncomeAmount, textViewSavingsAmount, textViewLoanAmount;

    public BigExpenseViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewIncomeAmount = itemView.findViewById(R.id.textViewBigExpensePlanIncomesAmount);
        textViewSavingsAmount = itemView.findViewById(R.id.textViewBigExpensePlanSavingsAmount);
        textViewLoanAmount = itemView.findViewById(R.id.textViewBigExpensePlanLoanAmount);
    }
}
