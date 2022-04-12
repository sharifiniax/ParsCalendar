package com.sharifiniax.parscalendar.ui.todo.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharifiniax.parscalendar.data.TodoCategory
import com.sharifiniax.parscalendar.databinding.CategoryItemBinding

class CategoryAdapter :
    ListAdapter<TodoCategory, CategoryAdapter.ViewHolder>(TodoCategoryDiffCallback()) {


    class ViewHolder
    private constructor(
        private val binding: CategoryItemBinding,
        ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: TodoCategory
        ) {
            binding.item=item
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }


}

class TodoCategoryDiffCallback : DiffUtil.ItemCallback<TodoCategory>() {
    override fun areItemsTheSame(oldItem: TodoCategory, newItem: TodoCategory): Boolean {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: TodoCategory, newItem: TodoCategory): Boolean {
        return oldItem == newItem

    }

}

