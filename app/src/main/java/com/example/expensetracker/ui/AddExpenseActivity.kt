package com.example.expensetracker.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.expensetracker.R
import com.example.expensetracker.data.Expense
import com.example.expensetracker.databinding.ActivityAddExpenseBinding
import com.example.expensetracker.viewmodel.ExpenseViewModel
import java.util.Date

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories = arrayOf("Food", "Transport", "Entertainment", "Utilities", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        binding.categorySpinner.adapter = adapter

        binding.saveButton.setOnClickListener {
            saveExpense()
        }
    }

    private fun saveExpense() {
        val title = binding.titleEditText.text.toString()
        val amount = binding.amountEditText.text.toString().toDoubleOrNull()
        val category = binding.categorySpinner.selectedItem.toString()

        if (title.isBlank() || amount == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Expense(title = title, amount = amount, category = category, date = Date())
        expenseViewModel.insert(expense)
        finish()
    }
}