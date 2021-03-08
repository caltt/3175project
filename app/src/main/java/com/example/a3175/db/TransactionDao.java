package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao extends BaseDao<Transaction> {

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    Transaction selectById(int id);

    @Query("SELECT * FROM `transaction` WHERE user_id = :userId ORDER BY date DESC")
    LiveData<List<Transaction>> selectByUserId(int userId);

    @Query("SELECT * FROM `transaction` INNER JOIN category " +
            "ON `transaction`.category_id = category.id " +
            "WHERE `transaction`.id = :id")
    Category selectCategoryById(int id);
}
