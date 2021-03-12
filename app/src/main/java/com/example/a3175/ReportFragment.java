package com.example.a3175;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3175.db.Transaction;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportFragment extends BaseFragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        LocalDate localDate = LocalDate.now();
//        String yearMonth = localDate.format(DateTimeFormatter.ofPattern("uuuu-MM"));
//        List<Transaction> transactions = transactionViewModel.getByUserIdYearMonth(currentUserId, yearMonth);
//        for (Transaction t : transactions) {
//            Log.d(TAG, "onActivityCreated: " + t.getId() + " " + t.getAmount());
//        }

        //region VIEW
        tabLayout = activity.findViewById(R.id.tabLayout);
        viewPager = activity.findViewById(R.id.viewPager);
        //endregion

        //region VIEWPAGER
        // adapter
        viewPager.setAdapter(new FragmentStateAdapter(activity) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new ReportTextFragment();
                    case 1:
                        return new ReportLineChartFragment();
                    case 2:
                        return new ReportPieChartFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        // mediator
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText(getResources().getString(R.string.tab_report_text));
                    break;
                case 1:
                    tab.setText(getResources().getString(R.string.tab_report_line_chart));
                    break;
                case 2:
                    tab.setText(getResources().getString(R.string.tab_report_pie_chart));
                    break;
            }
        }).attach();
        //endregion
    }
}