package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OverviewViewModel extends BaseViewModel {
    public OverviewViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Overview> getByUserId(int id){return repository.getOverviewByUserId(id);}

    public Overview getByUserIdForUpdate(int id){return repository.getOverviewByUserIdForUpdate(id);}

    public void insert(Overview... overviews) {
        repository.insertOverviews(overviews);
    }

    public void update(Overview... overviews) {
        repository.updateOverviews(overviews);
    }

    public void delete(Overview... overviews) {
        repository.deleteOverviews(overviews);
    }
}
