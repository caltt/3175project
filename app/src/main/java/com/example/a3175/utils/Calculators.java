package com.example.a3175.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.a3175.db.Overview;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Calculators {
    public static void processTransaction(Overview overview, BigDecimal transactionAmount) {
        updateIncomesSavings(overview, transactionAmount);
        updateTodayRemaining(overview, transactionAmount);
        // update today allowed
    }

    public static void processRecurringTransaction(Overview overview, BigDecimal transactionAmount) {
        updateIncomesSavings(overview, transactionAmount);
    }

    /**
     * Calculate daily allowed until next income date
     *
     * @param overview
     * @param nextIncomeDate
     */
    public static void resetTodayAllowed(Overview overview, int nextIncomeDate) {
        LocalDate today = LocalDate.now();
        int remainingDays = today.getDayOfMonth() < nextIncomeDate
                ? nextIncomeDate - today.getDayOfMonth()    // same month
                : nextIncomeDate + today.lengthOfMonth() - today.getDayOfMonth();   // next month

        BigDecimal todayAllowed = overview.getIncomes()
                .divide(new BigDecimal(remainingDays), 2, BigDecimal.ROUND_HALF_UP);

        overview.setSavings(overview.getSavings().add(overview.getTodayRemaining()));
        overview.setTodayAllowed(todayAllowed);
        overview.setTodayRemaining(todayAllowed);
    }

    private static void updateTodayRemainingAllowed(Overview overview) {
        LocalDate today = LocalDate.now();
        int remainingDays = today.lengthOfMonth() - today.getDayOfMonth() + 1;
        BigDecimal todayAllowed = overview.getIncomes()
                .divide(new BigDecimal(remainingDays), 2, BigDecimal.ROUND_HALF_UP);

        BigDecimal oldTodayAllowed = overview.getTodayAllowed();
        overview.setTodayRemaining(overview.getTodayRemaining().add(todayAllowed).subtract(oldTodayAllowed));
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
     */
    private static void updateIncomesSavings(Overview overview, BigDecimal transactionAmount) {
        BigDecimal incomes = overview.getIncomes();
        BigDecimal savings = overview.getSavings();

        if (transactionAmount.compareTo(BigDecimal.ZERO) >= 0) {
            // 1. INCOME
            if (savings.compareTo(BigDecimal.ZERO) < 0) {

                // go to savings first
                savings = savings.add(transactionAmount);

                // after savings reach 0, the rest go to incomes
                if (savings.compareTo(BigDecimal.ZERO) > 0) {
                    incomes = incomes.add(savings);
                    savings = BigDecimal.ZERO;
                }
            } else {

                // all go to incomes
                incomes = incomes.add(transactionAmount);
            }

            // TODO: update today allowed
            updateTodayRemainingAllowed(overview);
        } else {
            // 2. EXPENSE
            transactionAmount = transactionAmount.negate();    // make it positive
            if (incomes.compareTo(transactionAmount) >= 0) {

                // cost from incomes
                incomes = incomes.subtract(transactionAmount);
            } else {

                // cost from incomes first
                transactionAmount = transactionAmount.subtract(incomes);
                incomes = BigDecimal.ZERO;

                // after incomes reach 0, cost the rest from savings
                savings = savings.subtract(transactionAmount);
            }


        }

        // update entry
        overview.setIncomes(incomes);
        overview.setSavings(savings);

    }

    private static void updateTodayRemaining(Overview overview, BigDecimal transactionAmount) {
        if (transactionAmount.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal todayRemaining = overview.getTodayRemaining();

            // cost from today remaining until it reaches 0
            todayRemaining = BigDecimal.ZERO.max(todayRemaining.add(transactionAmount));
            overview.setTodayRemaining(todayRemaining);
        }
    }
}
