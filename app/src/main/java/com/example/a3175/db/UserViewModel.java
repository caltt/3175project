package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends BaseViewModel {

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<User>> getAll() {
        return repository.getAllUsers();
    }

    public User getById(int id) {
        return repository.getUserById(id);
    }

    public User getByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    public User getByEmailPassword(String email, String password) {
        return repository.getUserByEmailPassword(email, password);
    }

    public void insert(User... users) {
        repository.insertUsers(users);
    }

    public void update(User... users) {
        repository.updateUsers(users);
    }

    public void delete(User... users) {
        repository.deleteUsers(users);
    }
}
