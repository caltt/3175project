package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecurringTransactionDao {
    @Insert
    void insertRecurringTransactions(RecurringTransaction... transactions);

    @Update
    void updateRecurringTransactions(RecurringTransaction... transactions);

    @Delete
    void deleteRecurringTransactions(RecurringTransaction... transactions);

    @Query("SELECT * FROM recurringtransaction WHERE id = :id")
    RecurringTransaction getRecurringTransactionById(int id);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId")
    LiveData<List<RecurringTransaction>> getRecurringTransactionsByUserId(int userId);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId AND amount < 0 ORDER BY date ASC")
    LiveData<List<RecurringTransaction>> getRecurringExpensesByUserId(int userId);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId AND amount > 0 ORDER BY date ASC")
    LiveData<List<RecurringTransaction>> getRecurringIncomesByUserId(int userId);
}
