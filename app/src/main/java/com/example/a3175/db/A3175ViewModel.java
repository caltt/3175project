package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class A3175ViewModel<T> extends AndroidViewModel {
    A3175Repository repository;
    A3175Database database ;

    protected A3175ViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
        database = A3175Database.getDatabase(application);
    }

    protected void insert(T... t){
        database.
    }


}
