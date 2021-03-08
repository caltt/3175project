package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private A3175Repository repository;


    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
    }

    public Category getById(int id) {
        return repository.getCategoryById(id);
    }

    public Category getByName(String name) {
        return repository.getCategoryByName(name);
    }

    public LiveData<List<Category>> getAllCategories() {
        return repository.getAllCategories();
    }

    public LiveData<List<Category>> getAllIncomeCategories() {
        return repository.getAllIncomeCategories();
    }

    public LiveData<List<Category>> getAllExpenseCategories() {
        return repository.getAllExpenseCategories();
    }

    public void insert(Category... categories) {
        repository.insertCategories(categories);
    }

    public void update(Category... categories) {
        repository.updateCategories(categories);
    }

    public void delete(Category... categories) {
        repository.deleteCategories(categories);
    }
}
