package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a3175.db.User;
import com.example.a3175.db.UserAdapter;
import com.example.a3175.db.UserViewModel;
import com.example.a3175.utils.AppManager;

import java.util.List;

public class AdminFragment extends Fragment {
    FragmentActivity activity;
    UserViewModel userViewModel;
    NavController navController;

    UserAdapter userAdapter;

    RecyclerView recyclerView;
    Button buttonCreateAccount;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        activity = requireActivity();
        navController = AppManager.getNavController();
        userViewModel = AppManager.getUserViewModel();
        userAdapter = new UserAdapter(R.layout.cell_user);

        // setup view
        recyclerView = activity.findViewById(R.id.recyclerViewUser);
        buttonCreateAccount = activity.findViewById(R.id.buttonAdminCreateAccount);

        // recycle view
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(userAdapter);

        LiveData<List<User>> liveDataUser = userViewModel.getAllUsers();
        liveDataUser.observe(getViewLifecycleOwner(), users -> userAdapter.submitList(liveDataUser.getValue()));

        // button
        buttonCreateAccount.setOnClickListener(v -> {
            navController.navigate(R.id.action_adminFragment_to_createAccountFragment);
        });
    }
}