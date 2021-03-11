package com.example.a3175;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3175.db.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportFragment extends BaseFragment {

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

        LocalDate localDate = LocalDate.now();
        String yearMonth = localDate.format(DateTimeFormatter.ofPattern("uuuu-MM"));
        List<Transaction> transactions = transactionViewModel.getByUserIdYearMonth(currentUserId, yearMonth);
        for (Transaction t : transactions) {
            Log.d(TAG, "onActivityCreated: " + t.getId() + " " + t.getAmount());

        }
    }
}