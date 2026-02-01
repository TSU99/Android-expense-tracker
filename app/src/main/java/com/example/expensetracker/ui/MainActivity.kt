package com.example.expensetracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityMainBinding
import com.example.expensetracker.ui.adapter.ExpenseAdapter
import com.example.expensetracker.viewmodel.ExpenseViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        expenseViewModel.allExpenses.observe(this, Observer {
            if (it.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE
                expenseAdapter.submitList(it)
            }
        })

        expenseViewModel.totalMonthlyExpense.observe(this, Observer {
            binding.totalAmountTextView.text = getString(R.string.total_this_month, it ?: 0.0)
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter { expense ->
            expenseViewModel.delete(expense)
        }
        binding.recyclerView.adapter = expenseAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}