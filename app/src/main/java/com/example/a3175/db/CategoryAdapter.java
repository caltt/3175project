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

public class CategoryAdapter extends ListAdapter<Category, CategoryViewHolder> {
    private NavController navController;
    private final int layoutId;
    private boolean isForTransaction;

    public CategoryAdapter(Activity activity, int layoutId, boolean isForTransaction) {
        super(new DiffUtil.ItemCallback<Category>() {
            @Override
            public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        });
        this.navController = Navigation.findNavController(activity, R.id.navHostFragmentMainActivity);
        this.layoutId = layoutId;
        this.isForTransaction = isForTransaction;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // itemView
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutId, parent, false);

        // viewHolder
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getItem(position);

        holder.textViewCategoryName.setText(category.getName());

        // nav to add transaction or edit category
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", category.getId());
            navController.navigate(isForTransaction ? R.id.action_categoryFragment_to_editTransactionFragment
                    : R.id.action_categoryFragment_to_editCategoryFragment, bundle);
        });
    }
}
