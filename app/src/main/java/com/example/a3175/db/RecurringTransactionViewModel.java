package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.sql.Date;
import java.util.List;

public class RecurringTransactionViewModel extends AndroidViewModel {
    private A3175Repository repository;

    public RecurringTransactionViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
    }

    public RecurringTransaction getById(int id) {
        return repository.getRecurringTransactionById(id);
    }

    public LiveData<List<RecurringTransaction>> getByUserId(int id) {
        return repository.getRecurringTransactionsByUserId(id);
    }

    public LiveData<List<RecurringTransaction>> getRecurringExpensesByUserId(int id) {
        return repository.getRecurringExpensesByUserId(id);
    }

    public LiveData<List<RecurringTransaction>> getRecurringIncomesByUserId(int id) {
        return repository.getRecurringIncomesByUserId(id);
    }

    public List<RecurringTransaction> getByUserIdBetweenDates(int id, int from, int to) {
        return repository.getRecurringTransactionsByUserIdBetweenDates(id, from, to);
    }

    public List<RecurringTransaction> getByUserIdDate(int id, int date) {
        return repository.getRecurringTransactionsByUserIdDate(id, date);
    }

    public void insert(RecurringTransaction... transactions) {
        repository.insertRecurringTransactions(transactions);
    }

    public void update(RecurringTransaction... transactions) {
        repository.updateRecurringTransactions(transactions);
    }

    public void delete(RecurringTransaction... transactions) {
        repository.deleteRecurringTransactions(transactions);
    }

}
