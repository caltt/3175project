package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecurringTransactionViewModel extends AndroidViewModel {
    private A3175Repository repository;

    public RecurringTransactionViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
    }

    public RecurringTransaction getRecurringTransactionById(int id) {
        return repository.getRecurringTransactionById(id);
    }
    public LiveData<List<RecurringTransaction>> getRecurringTransactionsByUserId(int id) {
        return repository.getRecurringTransactionsByUserId(id);
    }

    public LiveData<List<RecurringTransaction>> getRecurringExpensesByUserId(int id){
        return repository.getRecurringExpensesByUserId(id);
    }

    public LiveData<List<RecurringTransaction>> getRecurringIncomesByUserId(int id){
        return repository.getRecurringIncomesByUserId(id);
    }

    public void insertRecurringTransactions(RecurringTransaction... transactions) {
        repository.insertRecurringTransactions(transactions);
    }

    public void updateRecurringTransactions(RecurringTransaction... transactions) {
        repository.updateRecurringTransactions(transactions);
    }

    public void deleteRecurringTransactions(RecurringTransaction... transactions) {
        repository.deleteRecurringTransactions(transactions);
    }

}
