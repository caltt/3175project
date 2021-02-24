package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BillDao {
    @Insert
    void insertBills(Bill... bills);

    @Update
    void updateBills(Bill... bills);

    @Delete
    void deleteBills(Bill... bills);

    @Query("SELECT * FROM bill WHERE user_id = :userId")
    LiveData<List<Bill>> getBillsByUserId(int userId);
}
