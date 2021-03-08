package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao extends BaseDao<Category>{

    @Query("SELECT * FROM category WHERE id = :id")
    Category selectById(int id);

    @Query("SELECT * FROM category WHERE name = :name")
    Category selectByName(String name);

    @Query("SELECT * FROM category")
    LiveData<List<Category>> selectAll();

    @Query("SELECT * FROM category WHERE is_income = 0")
    LiveData<List<Category>> selectAllExpenseCategories();

    @Query("SELECT * FROM category WHERE is_income = 1")
    LiveData<List<Category>> selectAllIncomeCategories();

}
