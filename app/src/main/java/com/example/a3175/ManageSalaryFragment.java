package com.example.a3175;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ManageSalaryFragment extends Fragment {
    FragmentActivity activity;
    NavController navController;

    Button buttonToAddSalary;

    public ManageSalaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_salary, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = requireActivity();
        navController = Navigation.findNavController(activity, R.id.navHostFragment);

        buttonToAddSalary = activity.findViewById(R.id.buttonManageSalaryAdd);

        buttonToAddSalary.setOnClickListener(v -> {
            navController.navigate(R.id.action_manageSalaryFragment_to_addSalaryFragment);
        });
    }
}