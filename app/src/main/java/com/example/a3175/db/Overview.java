package com.example.a3175.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity
public class Overview {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "incomes")
    private BigDecimal incomes;
    @ColumnInfo(name = "savings")
    private BigDecimal savings;
    @ColumnInfo(name = "today_allowed")
    private BigDecimal todayAllowed;
    @ColumnInfo(name = "today_remaining")
    private BigDecimal todayRemaining;

    public Overview(int userId, BigDecimal incomes, BigDecimal savings, BigDecimal todayAllowed, BigDecimal todayRemaining) {
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

    public BigDecimal getIncomes() {
        return incomes;
    }

    public void setIncomes(BigDecimal incomes) {
        this.incomes = incomes;
    }

    public BigDecimal getSavings() {
        return savings;
    }

    public void setSavings(BigDecimal savings) {
        this.savings = savings;
    }

    public BigDecimal getTodayAllowed() {
        return todayAllowed;
    }

    public void setTodayAllowed(BigDecimal todayAllowed) {
        this.todayAllowed = todayAllowed;
    }

    public BigDecimal getTodayRemaining() {
        return todayRemaining;
    }

    public void setTodayRemaining(BigDecimal todayRemaining) {
        this.todayRemaining = todayRemaining;
    }
}
