package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IncomeDao {
    @Insert
    void insertIncomes(Income... incomes);

    @Update
    void updateIncomes(Income... incomes);

    @Delete
    void deleteIncomes(Income... incomes);

    @Query("SELECT * FROM income WHERE user_id = :userId")
    LiveData<List<Income>> getIncomesByUserId(int userId);
}
