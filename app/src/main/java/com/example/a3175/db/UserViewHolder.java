package com.example.a3175.db;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    TextView textViewUserId, textViewUserEmail;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewUserId = itemView.findViewById(R.id.textViewUserId);
        textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail);
    }
}
