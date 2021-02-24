package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insertExpenses(Expense... expenses);

    @Update
    void updateExpenses(Expense... expenses);

    @Delete
    void deleteExpenses(Expense... expenses);

    @Query("SELECT * FROM expense WHERE user_id = :userId")
    LiveData<List<Expense>> getExpensesByUserId(int userId);
}
