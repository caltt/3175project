package com.example.a3175.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "is_income")
    private boolean isIncome;

    @Ignore
    public Category(int id, String name, boolean isIncome) {
        this.id = id;
        this.name = name;
        this.isIncome = isIncome;
    }

    public Category(String name, boolean isIncome) {
        this.name = name;
        this.isIncome = isIncome;
    }

    public static Category[] getPrePopulateData(){
        return new Category[]{
                new Category(1, "Salary", true),
                new Category(2, "Bill", false),
                new Category("income1", true),
                new Category("expense1", false),
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }
}
