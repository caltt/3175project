package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class OverviewViewModel extends AndroidViewModel {
    private A3175Repository repository;

    public OverviewViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
    }
    public LiveData<Overview> getOverviewByUserId(int id){return repository.getOverviewByUserId(id);}

    public void insertOverviews(Overview... overviews) {
        repository.insertOverviews(overviews);
    }

    public void updateOverviews(Overview... overviews) {
        repository.updateOverviews(overviews);
    }

    public void deleteOverviews(Overview... overviews) {
        repository.deleteOverviews(overviews);
    }
}
