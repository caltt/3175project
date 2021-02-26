package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a3175.db.OverviewViewModel;
import com.example.a3175.db.TransactionViewModel;

public class ExpenseTrackerFragment extends Fragment {
    FragmentActivity activity;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavController navController;

    OverviewViewModel overviewViewModel;
    TransactionViewModel transactionViewModel;

    String TAG = "test";

    public ExpenseTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_tracker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        navController = Navigation.findNavController(activity, R.id.ConstraintLayoutFragmentMain);

//        mainFragment = getParentFragment().getParentFragment();
        int loggedInUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);

        // test
        TextView textView = requireActivity().findViewById(R.id.textViewExpenseTrackerTest);
        textView.setText(String.valueOf(loggedInUserId));

//        Button buttonLogout = activity.findViewById(R.id.buttonLogout);
//        buttonLogout.setOnClickListener(v -> {
//            // remove from spref
//            editor.remove(getResources().getString(R.string.logged_in_user_email)).apply();
//            // nav to login fragment
//            navController.navigate(R.id.action_mainFragment_to_loginFragment);
//        });
    }
}