package com.example.a3175;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a3175.db.Overview;
import com.example.a3175.db.RecurringTransaction;
import com.example.a3175.db.Transaction;
import com.example.a3175.db.TransactionAdapter;
import com.example.a3175.utils.Calculators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpenseTrackerFragment extends BaseFragment {

    TransactionAdapter adapter;

    RecyclerView recyclerView;
    TextView textViewTodayRemaining, textViewSavings, textViewIncome, textViewTodayAllowed;

//    int currentUserId;
//    Overview currentOverview;

    public ExpenseTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_tracker, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // contextual data
//        currentUserId = preferences.getInt(getResources().getString(R.string.logged_in_user_id), -1);
//        currentOverview = overviewViewModel.getByUserIdForUpdate(currentUserId);

        // setup
        adapter = new TransactionAdapter(categoryViewModel, navController, R.layout.cell_transaction);


        // view
        textViewTodayRemaining = activity.findViewById(R.id.textViewTodayRemainingAmount);
        textViewTodayAllowed = activity.findViewById(R.id.textViewTodayAllowedAmount);
        textViewSavings = activity.findViewById(R.id.textViewSavingsAmount);
        textViewIncome = activity.findViewById(R.id.textViewIncomeAmount);
        recyclerView = activity.findViewById(R.id.recyclerViewTransaction);

        //region OVERVIEW
        LiveData<Overview> liveDataOverview = overviewViewModel.getByUserId(currentUserId);
        liveDataOverview.observe(getViewLifecycleOwner(), overview -> {
            // calculate today's available from income & remainingDays
            textViewTodayRemaining.setText(
                    overview.getTodayRemaining().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            textViewTodayAllowed.setText(
                    overview.getTodayAllowed().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            textViewSavings.setText(
                    overview.getSavings().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            textViewIncome.setText(
                    overview.getIncomes().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
        });

        // UPDATE
        // a. login in the same day:    only load overview
        // b. login in a new day:       update today allowed
        //                              & set last login date
        //                              & check pending salary & bills

        LocalDate lastLoginDate;
        String lastLoginDateString = preferences.getString(getResources().getString(R.string.last_login_date) + currentUserId, "");
        lastLoginDate = lastLoginDateString.isEmpty()
                // for first login without a last login record
                // make last login = today to avoid unwanted check
                ? LocalDate.now()
                : LocalDate.parse(lastLoginDateString);

        if (!lastLoginDate.equals(LocalDate.now())) {
            // login in a new day (first login / another day login)

            // 1. check recurring transactions between last login date (exclusive) & today (inclusive)

            // get that period
            Period period = Period.between(lastLoginDate, LocalDate.now()); // start inclusive & end exclusive
            int duration = period.getDays();    // still a correct number of days

            // for each date during this period, get and do recurring transactions on that date
            LocalDate date;
            String description;
            BigDecimal amount;
            int categoryId;

            for (int i = 1; i <= duration; i++) {
                // get
                date = lastLoginDate.plusDays(i);
                List<RecurringTransaction> pendingRecurringTransactions
                        = recurringTransactionViewModel.getByUserIdDate(currentUserId, date.getDayOfMonth());

                // do transactions
                for (RecurringTransaction t : pendingRecurringTransactions) {
                    // db insert: transaction
                    categoryId = t.getCategoryId();
                    amount = t.getAmount();
                    description = t.getDescription() + " - " + date.getMonth();
                    transactionViewModel.insert(
                            new Transaction(currentUserId, amount, date, categoryId, description));

                    // db update: overview
                    Calculators.processRecurringTransaction(currentOverview, t.getAmount());
                }
            }

            // 2. update today available
            // get next income date
            LocalDate today = LocalDate.now();
            int todayDate = today.getDayOfMonth();
            int nextIncomeDate = today.lengthOfMonth();     // initial value

            List<RecurringTransaction> currentUserIncomes =
                    recurringTransactionViewModel.getRecurringIncomesByUserIdForUpdate(currentUserId);

            int minDaysBetween = 32;
            for (RecurringTransaction i : currentUserIncomes) {
                int incomeDate = i.getDate();
                int daysBetween = todayDate <= incomeDate
                        ? incomeDate - todayDate
                        : incomeDate + today.lengthOfMonth() - todayDate;
                if (daysBetween < minDaysBetween) {
                    minDaysBetween = daysBetween;
                    nextIncomeDate = incomeDate;
                }
            }

            Log.d(TAG, "onActivityCreated: " + nextIncomeDate);

            // reset today allowed
            Calculators.resetTodayAllowed(currentOverview, nextIncomeDate);

            // all overview changes done
            // db update
            overviewViewModel.update(currentOverview);
        } else {
            // login in the same day

            // doing nothing, can delete this part
        }
        // change last login date to today
        editor.putString(getResources().getString(R.string.last_login_date) + currentUserId, LocalDate.now().toString()).apply();
        //endregion

        //region TRANSACTION
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);

        // TODO
        // item decoration
//        GroupItemDecoration itemDecoration = new GroupItemDecoration();
//        recyclerView.addItemDecoration(itemDecoration);

        LiveData<List<Transaction>> liveDataTransaction = transactionViewModel.getByUserId(currentUserId);
        liveDataTransaction.observe(getViewLifecycleOwner(),
                transactions -> adapter.submitList(liveDataTransaction.getValue()));

        // TODO
        // swipe delete & update overview
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
                            // db delete: transaction
                            Transaction toDelete = liveDataTransaction.getValue().get(viewHolder.getAdapterPosition());
                            BigDecimal amount = toDelete.getAmount();
                            transactionViewModel.delete(toDelete);

                            // db update: overview
                            Calculators.processTransaction(currentOverview, amount.negate());
                            overviewViewModel.update(currentOverview);

                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        })
                        .create()
                        .show();
            }
        }).attachToRecyclerView(recyclerView);
        //endregion

    }
}