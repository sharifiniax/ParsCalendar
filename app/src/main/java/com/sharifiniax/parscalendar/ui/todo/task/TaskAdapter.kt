package com.sharifiniax.parscalendar.ui.todo.task


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharifiniax.parscalendar.data.Task
import com.sharifiniax.parscalendar.databinding.TaskItemBinding
import com.sharifiniax.parscalendar.ui.todo.TaskAction

class TaskAdapter(
    private val taskAction: TaskAction
):
    ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback()),DataChange {


    class ViewHolder
    private constructor(
        private val binding: TaskItemBinding,

        ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: Task,
            taskAction: TaskAction,
            interfaceDataChange: DataChange,
            position: Int
        ) {
            binding.item=item
            binding.taskAction=taskAction

            binding.taskBaseItem.setOnClickListener {
                item.let {
                    it.expand = !it.expand
                }
                interfaceDataChange.dataIsChanged(position)
            }


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
        holder.bind(item,taskAction,this,position)



    }


    override fun dataIsChanged(position:Int) {
        notifyItemChanged(position)
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

interface DataChange{
    fun dataIsChanged(position:Int)
}