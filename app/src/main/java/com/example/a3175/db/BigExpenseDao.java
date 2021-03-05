package com.example.a3175.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BigExpenseDao extends BaseDao<BigExpense>{
    @Query("SELECT * FROM bigexpense WHERE id == :id")
    BigExpense getById(int id);

    @Query("SELECT * FROM bigexpense WHERE user_id = :userId")
    List<BigExpense> getByUserId(int userId);
}
