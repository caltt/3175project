package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SalaryDao {
    @Insert
    void insertSalaries(Salary... salaries);

    @Update
    void updateSalaries(Salary... salaries);

    @Delete
    void deleteSalaries(Salary... salaries);

    @Query("SELECT * FROM salary WHERE user_id = :userId")
    LiveData<List<Salary>> getSalariesByUserId(int userId);
}
