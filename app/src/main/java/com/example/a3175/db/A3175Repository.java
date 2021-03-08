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

    private A3175Repository(Context context) {
        A3175Database database = A3175Database.getDatabase(context.getApplicationContext());
        userDao = database.getUserDao();
        overviewDao = database.getOverviewDao();
        transactionDao = database.getTransactionDao();
        recurringTransactionDao = database.getRecurringTransactionDao();
        categoryDao = database.getCategoryDao();
    }

    public static A3175Repository getInstance(Context context) {
        if (instance == null) {
            instance = new A3175Repository(context);
        }
        return instance;
    }

    // user
    LiveData<List<User>> getAllUsers() {
        return userDao.selectAll();
    }

    User getUserByEmail(String email) {
        return userDao.selectByEmail(email);
    }

    User getUserById(int id) {
        return userDao.selectById(id);
    }

    User getUserByEmailPassword(String email, String password) {
        return userDao.selectByEmailPassword(email, password);
    }

    void insertUsers(User... users) {
        userDao.insert(users);
    }

    void updateUsers(User... users) {
        userDao.update(users);
    }

    void deleteUsers(User... users) {
        userDao.delete(users);
    }

    // overview
    LiveData<Overview> getOverviewByUserId(int id) {
        return overviewDao.selectByUserId(id);
    }

    Overview getOverviewByUserIdForUpdate(int id) {
        return overviewDao.selectByUserIdForUpdate(id);
    }

    void insertOverviews(Overview... overviews) {
        overviewDao.insert(overviews);
    }

    void updateOverviews(Overview... overviews) {
        overviewDao.update(overviews);
    }

    void deleteOverviews(Overview... overviews) {
        overviewDao.delete(overviews);
    }

    // recurring transaction
    RecurringTransaction getRecurringTransactionById(int id) {
        return recurringTransactionDao.selectById(id);
    }

    LiveData<List<RecurringTransaction>> getRecurringTransactionsByUserId(int id) {
        return recurringTransactionDao.selectByUserId(id);
    }

    LiveData<List<RecurringTransaction>> getRecurringExpensesByUserId(int id) {
        return recurringTransactionDao.selectRecurringExpensesByUserId(id);
    }

    LiveData<List<RecurringTransaction>> getRecurringIncomesByUserId(int id) {
        return recurringTransactionDao.selectRecurringIncomesByUserId(id);
    }

    List<RecurringTransaction> getRecurringTransactionsByUserIdBetweenDates(int id, int from, int to) {
        return recurringTransactionDao.selectByUserIdBetweenDates(id, from, to);
    }

    List<RecurringTransaction> getRecurringTransactionsByUserIdDate(int id, int date) {
        return recurringTransactionDao.selectByUserIdDate(id, date);
    }

    void insertRecurringTransactions(RecurringTransaction... transactions) {
        recurringTransactionDao.insert(transactions);
    }

    void updateRecurringTransactions(RecurringTransaction... transactions) {
        recurringTransactionDao.update(transactions);
    }

    void deleteRecurringTransactions(RecurringTransaction... transactions) {
        recurringTransactionDao.delete(transactions);
    }


    // transaction
    Transaction getTransactionById(int id) {
        return transactionDao.selectById(id);
    }

    LiveData<List<Transaction>> getTransactionsByUserId(int id) {
        return transactionDao.selectByUserId(id);
    }

    Category getCategoryByTransactionId(int id) {
        return transactionDao.selectCategoryById(id);
    }

    void insertTransactions(Transaction... transactions) {
        transactionDao.insert(transactions);
    }

    void updateTransactions(Transaction... transactions) {
        transactionDao.update(transactions);
    }

    void deleteTransactions(Transaction... transactions) {
        transactionDao.delete(transactions);
    }

    // category
    Category getCategoryById(int id) {
        return categoryDao.selectById(id);
    }

    Category getCategoryByName(String name) {
        return categoryDao.selectByName(name);
    }

    LiveData<List<Category>> getAllCategories() {
        return categoryDao.selectAll();
    }

    LiveData<List<Category>> getAllIncomeCategories() {
        return categoryDao.selectAllIncomeCategories();
    }

    LiveData<List<Category>> getAllExpenseCategories() {
        return categoryDao.selectAllExpenseCategories();
    }

    void insertCategories(Category... categories) {
        categoryDao.insert(categories);
    }

    void updateCategories(Category... categories) {
        categoryDao.update(categories);
    }

    void deleteCategories(Category... categories) {
        categoryDao.delete(categories);
    }
}
