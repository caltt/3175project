package com.example.a3175.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class A3175Repository {
    private static UserDao userDao;
    private static OverviewDao overviewDao;
    private static SalaryDao salaryDao;
    private static BillDao billDao;
    private static IncomeDao incomeDao;
    private static ExpenseDao expenseDao;
    private static CategoryDao categoryDao;
    private static BigExpenseDao bigExpenseDao;

    public A3175Repository(Context context) {
        A3175Database database = A3175Database.getDatabase(context.getApplicationContext());
        userDao = database.getUserDao();
        overviewDao = database.getOverviewDao();


    }

    // user
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
    List<Overview> getOverviewByUserId(int id) {
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

    // salary
    LiveData<List<Salary>> getSalariesByUserId(int id) {
        return salaryDao.getSalariesByUserId(id);
    }

    void insertSalaries(Salary... salaries) {
        salaryDao.insertSalaries(salaries);
    }

    void updateSalaries(Salary... salaries) {
        salaryDao.updateSalaries(salaries);
    }

    void deleteSalaries(Salary... salaries) {
        salaryDao.deleteSalaries(salaries);
    }

    // bill
    LiveData<List<Bill>> getBillsByUserId(int id) {
        return billDao.getBillsByUserId(id);
    }

    void insertBills(Bill... bills) {
        billDao.insertBills(bills);
    }

    void updateBills(Bill... bills) {
        billDao.updateBills(bills);
    }

    void deleteBills(Bill... bills) {
        billDao.deleteBills(bills);
    }

    // expense

    // income

    // category
}
