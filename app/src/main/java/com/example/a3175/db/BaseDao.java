package com.example.a3175.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface BaseDao<T> {
    @Insert
    void insert(T... ts);

    @Update
    void update(T... ts);

    @Delete
    void delete(T... ts);
}
