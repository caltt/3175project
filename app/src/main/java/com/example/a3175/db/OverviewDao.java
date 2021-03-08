package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OverviewDao extends BaseDao<Overview>{

    @Query("SELECT * FROM overview WHERE user_id = :userId")
    LiveData<Overview> selectByUserId(int userId);

    @Query("SELECT * FROM overview WHERE user_id = :userId")
    Overview selectByUserIdForUpdate(int userId);
}
