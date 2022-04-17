package com.sharifiniax.parscalendar.ui.todo.calendar


import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.*

import com.sharifiniax.parscalendar.data.DayModel
import com.sharifiniax.parscalendar.data.MonthModel
import com.sharifiniax.parscalendar.databinding.BottomSheetItemForCalendarBinding
import com.sharifiniax.parscalendar.ui.todo.ICloseCalendarBottomSheet
import com.sharifiniax.parscalendar.utils.Utils


class CalendarsAdapter(

    private val closeCalendarBottomSheet: ICloseCalendarBottomSheet
) :
    ListAdapter<List<DayModel>, CalendarsAdapter.ViewHolder>(DayModelsDiffCallback()) {

    private var adapter = mutableListOf<CalendarAdapter>()

    class ViewHolder
    private constructor(
        private val binding: BottomSheetItemForCalendarBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: MutableList<DayModel>,
            adapter: CalendarAdapter
        ) {

            binding.calendarsItemRecyclerView
                .layoutManager = LinearLayoutManager(context)

            binding.calendarsItemRecyclerView.layoutManager =
                object : GridLayoutManager(context, 7) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }

            binding.calendarsItemRecyclerView.adapter = adapter
            adapter.submitList(
                item
            )
            val header = String.format(
                Utils.persianMonth(item[12].month) + " "
                        + item[12].year.toString()
            )
            binding.mothCalendar.text = header

            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    BottomSheetItemForCalendarBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding, parent.context)
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) as MutableList
        adapter.add(CalendarAdapter(closeCalendarBottomSheet))

        holder.bind(item, adapter[position])

    }


}

class DayModelsDiffCallback : DiffUtil.ItemCallback<List<DayModel>>() {
    override fun areItemsTheSame(oldItem: List<DayModel>, newItem: List<DayModel>): Boolean {
        return oldItem.size == newItem.size && oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: List<DayModel>, newItem: List<DayModel>): Boolean {
        return oldItem == newItem
    }

}

