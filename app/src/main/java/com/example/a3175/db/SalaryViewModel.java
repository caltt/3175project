package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SalaryViewModel extends AndroidViewModel {
    private A3175Repository repository;

    public SalaryViewModel(@NonNull Application application) {
        super(application);
        repository = new A3175Repository(application);
    }
    LiveData<List<Salary>> getSalariesByUserId(int id) {
        return repository.getSalariesByUserId(id);
    }

    public void insertSalaries(Salary... salaries) {
        repository.insertSalaries(salaries);
    }

    public void updateSalaries(Salary... salaries) {
        repository.updateSalaries(salaries);
    }

    public void deleteSalaries(Salary... salaries) {
        repository.deleteSalaries(salaries);
    }

}
