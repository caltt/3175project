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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {
    FragmentActivity activity;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavController navController;

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
        BottomNavigationView bottomNavigationView =activity.findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(activity, R.id.navHostFragmentMain);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();

        NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, navController, configuration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemLogout:
                // remove from spref
                editor.remove(getResources().getString(R.string.logged_in_user_id)).apply();

                // nav to login fragment
                navController.navigate(R.id.loginFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}