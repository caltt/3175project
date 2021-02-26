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

    LiveData<List<Transaction>> getTransactionsByUserId(int id){return repository.getTransactionsByUserId(id);}
    void insertTransactions(Transaction... transactions){
        repository.insertTransactions(transactions);
    }
    void updateTransactions(Transaction... transactions){repository.updateTransactions(transactions);}
    void deleteTransactions(Transaction... transactions){repository.deleteTransactions(transactions);}

}
