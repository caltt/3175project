package com.example.a3175.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.a3175.utils.Converters;

import java.sql.Date;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"),
        @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id")
})
@TypeConverters({Converters.class})
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "datetime")
    private Date datetime;
    @ColumnInfo(name = "description")
    private String description;

    public Expense(int userId, int categoryId, double amount, Date datetime, String description) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.datetime = datetime;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}