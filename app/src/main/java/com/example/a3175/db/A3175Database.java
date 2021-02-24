package com.example.a3175.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        User.class,
        Overview.class,
        Salary.class,
        Bill.class,
        Income.class,
        Expense.class,
        Category.class,
        BigExpense.class,
}, version = 3, exportSchema = false)
public abstract class A3175Database extends RoomDatabase {
    private static A3175Database instance;

    static synchronized A3175Database getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), A3175Database.class, "a3175_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    abstract UserDao getUserDao();

    abstract OverviewDao getOverviewDao();

    abstract SalaryDao getSalaryDao();

    abstract BillDao getBillDao();

}
