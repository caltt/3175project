package com.example.a3175.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BigExpenseDao {
    @Insert
    void insertBigExpenses(BigExpense... bigExpenses);

    @Update
    void updateBigExpenses(BigExpense... bigExpenses);

    @Delete
    void deleteBigExpenses(BigExpense... bigExpenses);

    @Query("SELECT * FROM bigexpense WHERE user_id = :userId")
    List<BigExpense> getBigExpensesByUserId(int userId);
}
