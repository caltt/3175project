package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUsers(User... users);

    @Update
    void updateUsers(User... users);

    @Delete
    void deleteUsers(User... users);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    List<User> getUserById(int id);

    @Query("SELECT * FROM user WHERE email = :email")
    List<User> getUserByEmail(String email);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    List<User> getUserByEmailPassword(String email, String password);
}
