package com.sharifiniax.parscalendar.ui.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.databinding.CalendarItemBinding
import com.sharifiniax.parscalendar.data.DayModel
import com.sharifiniax.parscalendar.data.MonthModel

class CalendarAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val selectItem: MutableLiveData<DayModel?>,
    private val eventTitles: EventTitles
) :
    ListAdapter<DayModel, CalendarAdapter.ViewHolder>(DayModelDiffCallback()){

    private val backColorObserver=MutableLiveData(false)


    class ViewHolder
    private constructor(
        private val binding: CalendarItemBinding,
        val context: Context
    ): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("ClickableViewAccessibility")
        fun bind(
            item: DayModel,
            backColorObserver: MutableLiveData<Boolean>,
            lifecycleOwner: LifecycleOwner,
            selectItem: MutableLiveData<DayModel?>,
            eventTitles: EventTitles
        ){

            binding.dayModel=item



            if (item.monthModel==MonthModel.Current){
                if (eventTitles.isJalaliEventExist(item)
                    || eventTitles.isHijriEventExist(item)
                    || eventTitles.isGregorianEventExist(item)

                ){
                    binding.eventExist.visibility=View.VISIBLE
                }
            }
            if (item.monthModel!=MonthModel.Current){
                    binding.eventExist.visibility=View.GONE
            }
            if ( item.monthModel == MonthModel.Previous
                || item.monthModel == MonthModel.Post
            ){
                binding.dayOfMonth.let {
                    it.setTextColor(ResourcesCompat.getColor(it.resources, R.color.blue_200,null))
                }
            }
            if (item.monthModel == MonthModel.Current) {
                binding.dayOfMonth.let {
                    it.setTextColor(ResourcesCompat.getColor(it.resources, R.color.white, null))
                }
            }

            backColorObserver.observe(lifecycleOwner) {
                if (it)
                    binding.baseItem.background=null

            }
            if (selectItem.value==item){
//                selectItem.value=item
                binding.baseItem.background=binding.baseItem.resources.getDrawable(R.drawable.item_calendar_shap)

            }else{
                binding.baseItem.background=null
            }
            binding.lifecycleOwner?.let {
                selectItem.observe(it){
                    backColorObserver.value = true
                    backColorObserver.value = false

                    binding.baseItem.background = binding.baseItem.resources.getDrawable(R.drawable.item_calendar_shap)



                }
            }








            binding.baseItem.setOnTouchListener(
                OnSwipeTouchListener(context,
                    object :ISwipe {
                        override fun onSwipeLeft() {
                            eventTitles.nextMonth()
                            selectItem.value=null
                            backColorObserver.value=true
                            backColorObserver.value=false
                        }

                        override fun onSwipeRight() {
                            eventTitles.previousMonth()
                            selectItem.value=null
                            backColorObserver.value=true
                            backColorObserver.value=false
                        }

                        override fun onTouch() {
                            if (item.monthModel==MonthModel.Current) {
                                item.let {
                                        selectItem.value = item
                                        backColorObserver.value = true
                                        backColorObserver.value = false

                                        binding.baseItem.background = binding.baseItem.resources.getDrawable(R.drawable.item_calendar_shap)



                                }
                            }
                            if (item.monthModel != MonthModel.Current) {

                                val s=null
                            }
                        }

                          override var swipe: Boolean = false
                    })
            )

            if (eventTitles.isHoliday(item) && item.monthModel==MonthModel.Current){
                binding.dayOfMonth.let {
                    it.setTextColor(ResourcesCompat.getColor(it.resources, R.color.yellow_400,null))
                }

            }
            if (item.dayOfWeek==6 && item.monthModel==MonthModel.Current){

                binding.dayOfMonth.let {
                    it.setTextColor(ResourcesCompat.getColor(it.resources, R.color.yellow_400,null))
                }

            }
            binding.executePendingBindings()

        }


        companion object{
            fun from( parent:ViewGroup):ViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding= CalendarItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding,parent.context)
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }





    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item,backColorObserver,lifecycleOwner,selectItem,eventTitles)

    }


}

class DayModelDiffCallback: DiffUtil.ItemCallback<DayModel>() {
    override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return oldItem==newItem
    }


    override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
        return oldItem==newItem

    }

}

