package com.example.a3175.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public interface RecurringTransactionDao extends BaseDao<RecurringTransaction> {

    @Query("SELECT * FROM recurringtransaction WHERE id = :id")
    RecurringTransaction selectById(int id);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId")
    LiveData<List<RecurringTransaction>> selectByUserId(int userId);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId AND amount < 0 ORDER BY date ASC")
    LiveData<List<RecurringTransaction>> selectRecurringExpensesByUserId(int userId);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId AND amount > 0 ORDER BY date ASC")
    LiveData<List<RecurringTransaction>> selectRecurringIncomesByUserId(int userId);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId AND amount > 0 ORDER BY date ASC")
    List<RecurringTransaction> selectRecurringIncomesByUserIdForUpdate(int userId);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId AND :from <= date AND date <= :to")
    List<RecurringTransaction> selectByUserIdBetweenDates(int userId, int from, int to);

    @Query("SELECT * FROM recurringtransaction WHERE user_id = :userId AND date = :date")
    List<RecurringTransaction> selectByUserIdDate(int userId, int date);
}
