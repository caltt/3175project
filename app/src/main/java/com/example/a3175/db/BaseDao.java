package com.example.a3175.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert
    void insert(T t);

    @Insert
    void insert(T[] ts);

    @Update
    void update(T t);

    @Update
    void update(T[] ts);

    @Delete
    void delete(T t);

    @Delete
    void delete(T[] ts);
}
