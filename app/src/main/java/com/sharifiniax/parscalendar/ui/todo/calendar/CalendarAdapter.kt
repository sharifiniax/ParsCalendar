package com.sharifiniax.parscalendar.ui.todo.calendar


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.data.DayModel
import com.sharifiniax.parscalendar.data.MonthModel
import com.sharifiniax.parscalendar.databinding.CalendarItemBinding
import com.sharifiniax.parscalendar.ui.todo.ICloseCalendarBottomSheet


class CalendarAdapter(
    private val closeCalendarBottomSheet: ICloseCalendarBottomSheet
) : ListAdapter<DayModel, CalendarAdapter.ViewHolder>(DayModelDiffCallback()) {


    class ViewHolder private constructor
        (
        private val binding: CalendarItemBinding,
        private val closeCalendarBottomSheet: ICloseCalendarBottomSheet

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: DayModel
        ) {
            if (item.monthModel == MonthModel.Current
                && !item.isAfter(closeCalendarBottomSheet.today.value!!)
            ){
                binding.dayModel = item
                binding.dayOfMonth.let {
                    it.setTextColor(ResourcesCompat.getColor(it.resources, R.color.blue_100,null))
                }

            }



            if (item.monthModel == MonthModel.Current
                && item.isAfter(closeCalendarBottomSheet.today.value!!)
            ) {
                binding.dayModel = item
                binding.dayOfMonth.let {
                    it.setTextColor(ResourcesCompat.getColor(it.resources, R.color.blue_400, null))
                }

                binding.dayOfMonth.setOnClickListener {
                    closeCalendarBottomSheet.closeCalendarBottomSheet(item)

                }
            }


            binding.executePendingBindings()
        }

        companion object {
            fun from(
                parent: ViewGroup,
                closeCalendarBottomSheet: ICloseCalendarBottomSheet
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CalendarItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, closeCalendarBottomSheet)
            }

        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, closeCalendarBottomSheet)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class DayModelDiffCallback() : DiffUtil.ItemCallback<DayModel>() {
    override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return oldItem == newItem
    }

}
