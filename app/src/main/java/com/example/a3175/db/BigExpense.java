package com.example.a3175.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class BigExpense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "datetime")
    private Date datetime;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "income_needed")
    private double incomeNeeded;
    @ColumnInfo(name = "saving_needed")
    private double savingNeeded;
    @ColumnInfo(name = "loan_needed")
    private double loanNeeded;
}
