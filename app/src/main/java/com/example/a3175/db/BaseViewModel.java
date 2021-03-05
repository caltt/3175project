package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class BaseViewModel<T> extends AndroidViewModel {
    A3175Repository repository;
    A3175Database database ;

    protected BaseViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
        database = A3175Database.getDatabase(application);
    }


}
