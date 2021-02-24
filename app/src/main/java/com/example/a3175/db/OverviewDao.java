package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OverviewDao {
    @Insert
    void insertOverviews(Overview... overviews);

    @Update
    void updateOverviews(Overview... overviews);

    @Delete
    void deleteOverviews(Overview... overviews);

    @Query("SELECT * FROM overview WHERE user_id = :userId")
    List<Overview> getOverviewByUserId(int userId);
}
