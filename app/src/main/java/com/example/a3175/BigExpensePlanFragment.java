package com.example.a3175;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3175.db.BigExpense;
import com.example.a3175.db.BigExpenseAdapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BigExpensePlanFragment extends BaseFragment {

    BigExpenseAdapter bigExpenseAdapter;

    RecyclerView recyclerViewPlan;
    SeekBar seekBarRatio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_big_expense_plan, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bigExpenseAdapter = new BigExpenseAdapter(activity, bigExpenseViewModel, R.layout.cell_big_expense_plan);

        recyclerViewPlan = activity.findViewById(R.id.recyclerViewBigExpensePlan);
        seekBarRatio = activity.findViewById(R.id.seekBar);

        recyclerViewPlan.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewPlan.setAdapter(bigExpenseAdapter);

        List<BigExpense> list = new ArrayList<>();
        list.add(new BigExpense(currentUserId, new BigDecimal(1000), LocalDate.now(), "",
                new BigDecimal(200), new BigDecimal(100), new BigDecimal(500)));
        list.add(new BigExpense(currentUserId, new BigDecimal(1000), LocalDate.now(), "",
                new BigDecimal(1200), new BigDecimal(100), new BigDecimal(500)));
        list.add(new BigExpense(currentUserId, new BigDecimal(1000), LocalDate.now(), "",
                new BigDecimal(200), new BigDecimal(1100), new BigDecimal(500)));

        bigExpenseAdapter.submitList(list);
    }

}