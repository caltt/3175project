package com.example.a3175.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

//public class UserViewModel extends AndroidViewModel {
//    private A3175Repository repository;
//
//    public UserViewModel(@NonNull Application application) {
//        super(application);
//        repository = A3175Repository.getInstance(application);
//    }
//
//
//    public LiveData<List<User>> getAllUsers() {
//        return repository.getAllUsers();
//    }
//
//    public User getUserById(int id) {
//        return repository.getUserById(id);
//    }
//
//    public User getUserByEmail(String email) {
//        return repository.getUserByEmail(email);
//    }
//
//    public User getUserByEmailPassword(String email, String password) {
//        return repository.getUserByEmailPassword(email, password);
//    }
//
//    public void insertUsers(User... users) {
//        repository.insertUsers(users);
//    }
//
//    public void updateUsers(User... users) {
//        repository.updateUsers(users);
//    }
//
//    public void deleteUsers(User... users) {
//        repository.deleteUsers(users);
//    }
//}

public class UserViewModel extends BaseViewModel<User> {
    private A3175Repository repository;
    private A3175Database database;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = A3175Repository.getInstance(application);
        database = A3175Database.getDatabase(application);
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
