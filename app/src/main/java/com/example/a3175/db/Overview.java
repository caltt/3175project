package com.example.a3175.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Overview {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "incomes")
    private double incomes;
    @ColumnInfo(name = "savings")
    private double savings;
    @ColumnInfo(name = "today_allowed")
    private double todayAllowed;
    @ColumnInfo(name = "today_remaining")
    private double todayRemaining;

    public Overview(int userId, double incomes, double savings, double todayAllowed, double todayRemaining) {
        this.userId = userId;
        this.incomes = incomes;
        this.savings = savings;
        this.todayAllowed = todayAllowed;
        this.todayRemaining = todayRemaining;
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

    public double getIncomes() {
        return incomes;
    }

    public void setIncomes(double incomes) {
        this.incomes = incomes;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getTodayAllowed() {
        return todayAllowed;
    }

    public void setTodayAllowed(double todayAllowed) {
        this.todayAllowed = todayAllowed;
    }

    public double getTodayRemaining() {
        return todayRemaining;
    }

    public void setTodayRemaining(double todayRemaining) {
        this.todayRemaining = todayRemaining;
    }
}
