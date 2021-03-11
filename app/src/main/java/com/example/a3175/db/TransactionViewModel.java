package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionViewModel extends BaseViewModel {

    public TransactionViewModel(@NonNull Application application) {
        super(application);
    }

    public Transaction getById(int id) {
        return repository.getTransactionById(id);
    }

    public Category getCategoryById(int id) {
        return repository.getCategoryByTransactionId(id);
    }

    public LiveData<List<Transaction>> getByUserId(int id) {
        return repository.getTransactionsByUserId(id);
    }

    public List<Transaction> getByUserIdYearMonth(int id, String yearMonth){
        return repository.getTransactionByUserIdYearMonth(id, yearMonth);
    }

    public void insert(Transaction... transactions) {
        repository.insertTransactions(transactions);
    }

    public void update(Transaction... transactions) {
        repository.updateTransactions(transactions);
    }

    public void delete(Transaction... transactions) {
        repository.deleteTransactions(transactions);
    }

}
