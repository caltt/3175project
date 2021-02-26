package com.example.a3175.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class A3175Repository {
    private static A3175Repository instance;

    private static UserDao userDao;
    private static OverviewDao overviewDao;
    private static RecurringTransactionDao recurringTransactionDao;
    private static TransactionDao transactionDao;
    private static CategoryDao categoryDao;
    private static BigExpenseDao bigExpenseDao;

    public A3175Repository(Context context) {
        A3175Database database = A3175Database.getDatabase(context.getApplicationContext());
        userDao = database.getUserDao();
        overviewDao = database.getOverviewDao();
        transactionDao = database.getTransactionDao();
        recurringTransactionDao = database.getRecurringTransactionDao();
    }

    public static A3175Repository getInstance(Context context) {
        if (instance == null) {
            instance = new A3175Repository(context);
        }
        return instance;
    }

    // user
    LiveData<List<User>> getAllUsers(){ return userDao.getAllUsers();}
    List<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    List<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    List<User> getUserByEmailPassword(String email, String password) {
        return userDao.getUserByEmailPassword(email, password);
    }

    void insertUsers(User... users) {
        userDao.insertUsers(users);
    }

    void updateUsers(User... users) {
        userDao.updateUsers(users);
    }

    void deleteUsers(User... users) {
        userDao.deleteUsers(users);
    }

    // overview
    LiveData<List<Overview>> getOverviewByUserId(int id) {
        return overviewDao.getOverviewByUserId(id);
    }

    void insertOverviews(Overview... overviews) {
        overviewDao.insertOverviews(overviews);
    }

    void updateOverviews(Overview... overviews) {
        overviewDao.updateOverviews(overviews);
    }

    void deleteOverviews(Overview... overviews) {
        overviewDao.deleteOverviews(overviews);
    }

    // recurring transaction
    LiveData<List<RecurringTransaction>> getRecurringTransactionsByUserId(int id) {
        return recurringTransactionDao.getRecurringTransactionsByUserId(id);
    }

    LiveData<List<RecurringTransaction>> getRecurringExpensesByUserId(int id){
        return recurringTransactionDao.getRecurringExpensesByUserId(id);
    }

    LiveData<List<RecurringTransaction>> getRecurringIncomesByUserId(int id){
        return recurringTransactionDao.getRecurringIncomesByUserId(id);
    }

    void insertRecurringTransactions(RecurringTransaction... transactions) {
        recurringTransactionDao.insertRecurringTransactions(transactions);
    }

    void updateRecurringTransactions(RecurringTransaction... transactions) {
        recurringTransactionDao.updateRecurringTransactions(transactions);
    }

    void deleteRecurringTransactions(RecurringTransaction... transactions) {
        recurringTransactionDao.deleteRecurringTransactions(transactions);
    }


    // transaction
    LiveData<List<Transaction>> getTransactionsByUserId(int id) {
        return transactionDao.getTransactionsByUserId(id);
    }

    void insertTransactions(Transaction... transactions) {
        transactionDao.insertTransactions(transactions);
    }

    void updateTransactions(Transaction... transactions) {
        transactionDao.updateTransactions(transactions);
    }

    void deleteTransactions(Transaction... transactions) {
        transactionDao.deleteTransactions(transactions);
    }

    // category
    LiveData<List<Category>> getCategories() {
        return categoryDao.getAllCategories();
    }

    void insertCategories(Category... categories) {
        categoryDao.insertCategories(categories);
    }

    void updateCategories(Category... categories) {
        categoryDao.updateCategories(categories);
    }

    void deleteCategories(Category... categories) {
        categoryDao.deleteCategories(categories);
    }
}
