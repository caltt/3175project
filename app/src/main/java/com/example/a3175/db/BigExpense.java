package com.example.a3175.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity()
public class BigExpense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "amount")
    private BigDecimal amount;
    @ColumnInfo(name = "datetime")
    private LocalDate datetime;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "income_needed")
    private BigDecimal incomeNeeded;
    @ColumnInfo(name = "saving_needed")
    private BigDecimal savingNeeded;
    @ColumnInfo(name = "loan_needed")
    private BigDecimal loanNeeded;

    public BigExpense(int userId, BigDecimal amount, LocalDate datetime, String description, BigDecimal incomeNeeded, BigDecimal savingNeeded, BigDecimal loanNeeded) {
        this.userId = userId;
        this.amount = amount;
        this.datetime = datetime;
        this.description = description;
        this.incomeNeeded = incomeNeeded;
        this.savingNeeded = savingNeeded;
        this.loanNeeded = loanNeeded;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public BigDecimal getIncomeNeeded() {
        return incomeNeeded;
    }

    public void setIncomeNeeded(BigDecimal incomeNeeded) {
        this.incomeNeeded = incomeNeeded;
    }

    public BigDecimal getSavingNeeded() {
        return savingNeeded;
    }

    public void setSavingNeeded(BigDecimal savingNeeded) {
        this.savingNeeded = savingNeeded;
    }

    public BigDecimal getLoanNeeded() {
        return loanNeeded;
    }

    public void setLoanNeeded(BigDecimal loanNeeded) {
        this.loanNeeded = loanNeeded;
    }
}
