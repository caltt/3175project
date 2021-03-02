package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
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

        // view
        recyclerView = activity.findViewById(R.id.recyclerViewUser);
        buttonCreateAccount = activity.findViewById(R.id.buttonAdminCreateAccount);

        // recycler view
        userAdapter = new UserAdapter(R.layout.cell_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(userAdapter);

        LiveData<List<User>> liveDataUser = userViewModel.getAllUsers();
        liveDataUser.observe(getViewLifecycleOwner(), users -> userAdapter.submitList(users));

        // button
        buttonCreateAccount.setOnClickListener(v -> {
            navController.navigate(R.id.action_adminFragment_to_editUserFragment);
        });
    }
}