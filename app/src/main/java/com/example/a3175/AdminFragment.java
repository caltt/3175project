package com.example.a3175;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a3175.db.User;
import com.example.a3175.db.UserAdapter;

import java.util.List;

public class AdminFragment extends BaseFragment {

    UserAdapter userAdapter;

    RecyclerView recyclerView;
    Button buttonCreateAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //region VIEW
        recyclerView = activity.findViewById(R.id.recyclerViewUser);
        buttonCreateAccount = activity.findViewById(R.id.buttonAdminCreateAccount);
        //endregion

        //region RECYCLER VIEW
        userAdapter = new UserAdapter(R.layout.cell_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(userAdapter);

        LiveData<List<User>> liveDataUser = userViewModel.getAllUsers();
        liveDataUser.observe(getViewLifecycleOwner(), users -> userAdapter.submitList(users));
        //endregion

        //region SWIPE DELETE
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(activity)
                        .setTitle("Delete?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            User userToDelete = liveDataUser.getValue().get(viewHolder.getAdapterPosition());
                            userViewModel.deleteUsers(userToDelete);

                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            userAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        })
                        .create()
                        .show();
            }
        }).attachToRecyclerView(recyclerView);
        //endregion

        //region BUTTON
        buttonCreateAccount.setOnClickListener(v -> {
            navController.navigate(R.id.action_adminFragment_to_editUserFragment);
        });
        //endregion
    }
}