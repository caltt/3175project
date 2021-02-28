package com.example.a3175.db;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    TextView textViewCategoryName;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewCategoryName = itemView.findViewById(R.id.textViewCategoryName);
    }
}
