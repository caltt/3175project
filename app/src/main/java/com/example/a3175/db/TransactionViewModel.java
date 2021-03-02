package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private A3175Repository repository;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
    }

    public Transaction getTransactionById(int id){
        return repository.getTransactionById(id);
    }

    public LiveData<List<Transaction>> getTransactionsByUserId(int id) {
        return repository.getTransactionsByUserId(id);
    }

    public void insertTransactions(Transaction... transactions) {
        repository.insertTransactions(transactions);
    }

    public void updateTransactions(Transaction... transactions) {
        repository.updateTransactions(transactions);
    }

    public void deleteTransactions(Transaction... transactions) {
        repository.deleteTransactions(transactions);
    }

}
