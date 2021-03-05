package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao extends BaseDao<Transaction>{

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    Transaction selectById(int id);

    @Query("SELECT * FROM `transaction` WHERE user_id = :userId ORDER BY datetime DESC")
    LiveData<List<Transaction>> selectByUserId(int userId);
}
