package com.example.expensetracker.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.data.Expense
import com.example.expensetracker.data.ExpenseDao

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()
    val totalMonthlyExpense: LiveData<Double> = expenseDao.getTotalMonthlyExpense()

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}