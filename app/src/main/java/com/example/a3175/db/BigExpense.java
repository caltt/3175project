package com.example.a3175.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.time.LocalDate;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class BigExpense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "datetime")
    private LocalDate datetime;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "income_needed")
    private double incomeNeeded;
    @ColumnInfo(name = "saving_needed")
    private double savingNeeded;
    @ColumnInfo(name = "loan_needed")
    private double loanNeeded;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getIncomeNeeded() {
        return incomeNeeded;
    }

    public void setIncomeNeeded(double incomeNeeded) {
        this.incomeNeeded = incomeNeeded;
    }

    public double getSavingNeeded() {
        return savingNeeded;
    }

    public void setSavingNeeded(double savingNeeded) {
        this.savingNeeded = savingNeeded;
    }

    public double getLoanNeeded() {
        return loanNeeded;
    }

    public void setLoanNeeded(double loanNeeded) {
        this.loanNeeded = loanNeeded;
    }
}
