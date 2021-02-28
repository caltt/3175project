package com.example.a3175.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {
        User.class,
        Overview.class,
        RecurringTransaction.class,
        Transaction.class,
        Category.class,
        BigExpense.class,
}, version = 6, exportSchema = false)
public abstract class A3175Database extends RoomDatabase {
    private static A3175Database instance;

    static synchronized A3175Database getDatabase(Context context) {
        if (instance == null) {
            // fill category data
            RoomDatabase.Callback callback = new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    // FIXME: fill some data
                }
            };

            instance = Room.databaseBuilder(context.getApplicationContext(), A3175Database.class, "a3175_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
//                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    abstract UserDao getUserDao();

    abstract OverviewDao getOverviewDao();

    abstract TransactionDao getTransactionDao();

    abstract CategoryDao getCategoryDao();

    abstract RecurringTransactionDao getRecurringTransactionDao();

    abstract BigExpenseDao getBigExpenseDao();
}
