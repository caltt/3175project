package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class BigExpenseViewModel extends AndroidViewModel {
    A3175Repository repository;
    public BigExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
    }

    // TODO
}
