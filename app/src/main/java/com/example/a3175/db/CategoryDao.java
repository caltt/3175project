package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategories(Category... categories);

    @Update
    void updateCategories(Category... categories);

    @Delete
    void deleteCategories(Category... categories);

    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAllCategories();
}
