package com.sharifiniax.parscalendar.ui.todo.task


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharifiniax.parscalendar.data.Task
import com.sharifiniax.parscalendar.databinding.TaskItemBinding

class TaskAdapter:
    ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback()) {


    class ViewHolder
    private constructor(
        private val binding: TaskItemBinding,

        ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: Task
        ) {
            binding.item=item
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
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

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem

    }

}

