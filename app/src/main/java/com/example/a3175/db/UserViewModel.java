package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private A3175Repository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
    }


    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public User getUserById(int id) {
        return repository.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    public User getUserByEmailPassword(String email, String password) {
        return repository.getUserByEmailPassword(email, password);
    }

    public void insertUsers(User... users) {
        repository.insertUsers(users);
    }

    public void updateUsers(User... users) {
        repository.updateUsers(users);
    }

    public void deleteUsers(User... users) {
        repository.deleteUsers(users);
    }
}
