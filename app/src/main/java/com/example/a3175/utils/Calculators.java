package com.example.a3175.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.a3175.db.Overview;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Calculators {
    public static void processTransaction(Overview overview, double transactionAmount) {
        updateIncomesSavings(overview, transactionAmount);
        updateTodayRemaining(overview, transactionAmount);
        // update today allowed
    }

    public static void processRecurringTransaction(Overview overview, double transactionAmount) {
        updateIncomesSavings(overview, transactionAmount);
    }

    public static void resetTodayAllowed(Overview overview) {
        LocalDate today = LocalDate.now();
        int remainingDays = today.lengthOfMonth() - today.getDayOfMonth() + 1;
        double todayAllowed = overview.getIncomes() / remainingDays;

        overview.setSavings(overview.getSavings() + overview.getTodayRemaining());
        overview.setTodayAllowed(todayAllowed);
        overview.setTodayRemaining(todayAllowed);
    }

    private static void updateTodayRemainingAllowed(Overview overview) {
        LocalDate today = LocalDate.now();
        int remainingDays = today.lengthOfMonth() - today.getDayOfMonth() + 1;
        double todayAllowed = overview.getIncomes() / remainingDays;

        double oldTodayAllowed = overview.getTodayAllowed();
        overview.setTodayRemaining(overview.getTodayRemaining() + todayAllowed - oldTodayAllowed);
        overview.setTodayAllowed(todayAllowed);
    }

    /**
     * Update overview after a transaction is done
     * <p>
     * 1. Income:
     * If savings < 0, go to savings until it reaches 0, the rest goes to incomes
     * When incomes change, today allowed changes accordingly
     * <p>
     * 2. Expense:
     * If incomes > 0, use incomes until it reaches 0, the rest is cost from savings
     * Change today remaining until it reaches 0
     *
     * @param overview
     * @param transactionAmount
     */
    private static void updateIncomesSavings(Overview overview, double transactionAmount) {
        double incomes = overview.getIncomes();
        double savings = overview.getSavings();

        if (transactionAmount >= 0) {
            // 1. INCOME
            if (savings < 0) {

                // go to savings first
                savings += transactionAmount;

                // after savings reach 0, the rest go to incomes
                if (savings > 0) {
                    incomes += savings;
                    savings = 0;
                }
            } else {

                // all go to incomes
                incomes += transactionAmount;
            }

            // TODO: update today allowed
            updateTodayRemainingAllowed(overview);
        } else {
            // 2. EXPENSE
            transactionAmount *= -1;    // make it positive
            if (incomes >= transactionAmount) {

                // cost from incomes
                incomes -= transactionAmount;
            } else {

                // cost from incomes first
                transactionAmount -= incomes;
                incomes = 0;

                // after incomes reach 0, cost the rest from savings
                savings -= transactionAmount;
            }


        }

        // update entry
        overview.setIncomes(incomes);
        overview.setSavings(savings);

    }

    private static void updateTodayRemaining(Overview overview, double transactionAmount) {
        if (transactionAmount < 0) {
            double todayRemaining = overview.getTodayRemaining();

            // cost from today remaining until it reaches 0
            todayRemaining = Math.max(0, todayRemaining + transactionAmount);
            overview.setTodayRemaining(todayRemaining);
        }
    }
}
