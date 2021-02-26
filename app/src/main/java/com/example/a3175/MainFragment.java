package com.example.a3175;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainFragment extends Fragment {
    private static final String TAG = "test";
    FragmentActivity activity;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavController navController;

    NavHostFragment navHostFragment;
    FloatingActionButton floatingActionButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup
        setHasOptionsMenu(true);

        activity = requireActivity();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
        navController = Navigation.findNavController(activity, R.id.ConstraintLayoutFragmentMain);

        // in case from login back to main
//        if (preferences.getString(getResources().getString(R.string.logged_in_user_email), null)==null){
//            navController.navigate(R.id.loginFragment);
//        }

        // setup bottom nav bar
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
        NavController navControllerBottomNav = Navigation.findNavController(activity, R.id.navHostFragmentMain);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();

        NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, navControllerBottomNav, configuration);
        NavigationUI.setupWithNavController(bottomNavigationView, navControllerBottomNav);

        // setup floating button
        floatingActionButton = activity.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            // nav based on current fragment id
            int currentFragmentId = navControllerBottomNav.getCurrentDestination().getId();
            if (currentFragmentId == R.id.expenseTrackerFragment) {
                navController.navigate(R.id.action_mainFragment_to_addTransactionFragment);
            } else if (currentFragmentId == R.id.bigExpensePlannerFragment) {
                navController.navigate(R.id.action_mainFragment_to_addBigExpenseFragment);
            }

        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSalaryBill:
                navController.navigate(R.id.action_mainFragment_to_manageRecurringTransactionFragment);
                break;
            case R.id.menuItemLogout:
                // remove from spref
                editor.remove(getResources().getString(R.string.logged_in_user_id)).apply();

                // nav to login fragment
                navController.navigateUp();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}