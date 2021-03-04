package com.example.a3175.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {
        User.class,
        Overview.class,
        RecurringTransaction.class,
        Transaction.class,
        Category.class,
        BigExpense.class,
}, version = 7, exportSchema = false)
public abstract class A3175Database extends RoomDatabase {
    private static A3175Database instance;

    static synchronized A3175Database getDatabase(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), A3175Database.class, "a3175_database")
//                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()

                    // fill some data
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                                getDatabase(context).getUserDao().insertUsers(User.getPrePopulateData());
                                getDatabase(context).getCategoryDao().insertCategories(Category.getPrePopulateData());

                            });
                        }
                    })
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
