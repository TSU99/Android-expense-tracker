package com.example.expensetracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.data.Expense
import com.example.expensetracker.databinding.ExpenseListItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseAdapter(private val onDeleteClicked: (Expense) -> Unit) : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.binding.deleteButton.setOnClickListener {
            onDeleteClicked(current)
        }
    }

    class ExpenseViewHolder(val binding: ExpenseListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fun bind(expense: Expense) {
            binding.titleTextView.text = expense.title
            binding.amountTextView.text = String.format("$%.2f", expense.amount)
            binding.categoryTextView.text = expense.category
            binding.dateTextView.text = dateFormat.format(expense.date)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Expense>() {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem == newItem
            }
        }
    }
}