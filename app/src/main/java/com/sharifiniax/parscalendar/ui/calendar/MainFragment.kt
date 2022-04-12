package com.sharifiniax.parscalendar.ui.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var adapter: CalendarAdapter


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel


        adapter = CalendarAdapter(
            viewLifecycleOwner, viewModel.selectItem, viewModel
        )
        binding.gridViewCalendar.adapter = adapter
        binding.gridViewCalendar.itemAnimator = null
        binding.gridViewCalendar.layoutManager = object : GridLayoutManager(context, 7) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        viewModel.listMonth.observe(viewLifecycleOwner) {
            it?.let {
//                viewModel.selectItem.value = viewModel.today.value
                adapter.submitList(it)

            }

        }
        viewModel.currentMonth.observe(viewLifecycleOwner) {

            binding.month.text = it

        }
        viewModel.currentYear.observe(viewLifecycleOwner) {

            binding.year.text = it

        }
        viewModel.selectItem.observe(viewLifecycleOwner) {

            if (it == null) {


                binding.dateBox.visibility = View.GONE
               binding.eventBox.visibility = View.GONE
                binding.lineUnderDateBox.visibility = View.GONE
                resetColorDayWeek()


            } else {

                binding.dateBox.visibility = View.VISIBLE
                binding.gregorianDate.text = viewModel.getGregorianDate(it)
                binding.hijriDate.text = viewModel.getHijriDate(it)
                binding.hijriDateAlphabetic.text = viewModel.hijriDateAlphabetic(it)
                binding.gregorianDateAlphabetic.text = viewModel.gregorianDateAlphabetic(it)





               binding.eventBox.visibility = View.GONE
                binding.lineUnderDateBox.visibility = View.GONE

                if (viewModel.isJalaliEventExist(it)) {
                   binding.eventBox.visibility = View.VISIBLE
                    binding.lineUnderDateBox.visibility = View.VISIBLE
                    binding.jalaliEvent.visibility = View.VISIBLE
                    viewModel.getJalaliEventTitles(it)
                } else {
                    binding.jalaliEvent.visibility = View.GONE
                }


                if (viewModel.isHijriEventExist(it)) {
                   binding.eventBox.visibility = View.VISIBLE
                    binding.lineUnderDateBox.visibility = View.VISIBLE
                    binding.hijriEvent.visibility = View.VISIBLE
                    viewModel.getHijriEventTitles(it)
                } else {
                    binding.hijriEvent.visibility = View.GONE
                }

                if (viewModel.isGregorianEventExist(it)) {
                   binding.eventBox.visibility = View.VISIBLE
                    binding.lineUnderDateBox.visibility = View.VISIBLE
                    binding.gregorianEvent.visibility = View.VISIBLE
                    viewModel.getGregorianEventTitles(it)
                } else {
                    binding.gregorianEvent.visibility = View.GONE
                }


                setColorDayWeekSelected(it.dayOfWeek)


            }
        }
        viewModel.jalaliEventTitles.observe(viewLifecycleOwner) {

            var holiday:Boolean?=null
            val stringBuilder = StringBuilder()
            it?.forEach { event ->
                stringBuilder.append(event.titles.faIR + "، ")
                if (event.holiday)
                    holiday=true
            }
            binding.jalaliEvent.let { event ->
                event.setTextColor(ResourcesCompat.getColor(event.resources, R.color.white,null))
            }
            binding.jalaliEvent.text = stringBuilder.toString()
            holiday?.let { bool ->
                if (bool){
                    binding.jalaliEvent.let { event ->
                        event.setTextColor(ResourcesCompat.getColor(event.resources, R.color.yellow_400,null))
                    }
                }
            }
        }
        viewModel.hijriEventTitles.observe(viewLifecycleOwner) {
            val stringBuilder = StringBuilder()
            var holiday:Boolean?=null
            it?.forEach { event ->
                stringBuilder.append(event.titles.faIR + "، ")
                if (event.holiday)
                    holiday=true
            }
            binding.hijriEvent.let { event ->
                event.setTextColor(ResourcesCompat.getColor(event.resources, R.color.white,null))
            }
            binding.hijriEvent.text = stringBuilder.toString()
            holiday?.let { bool ->
                if (bool){
                    binding.hijriEvent.let { event ->
                        event.setTextColor(ResourcesCompat.getColor(event.resources, R.color.yellow_400,null))
                    }
                }
            }
        }
        viewModel.gregorianEventTitles.observe(viewLifecycleOwner) {
            val stringBuilder = StringBuilder()
            it?.forEach { event ->
                stringBuilder.append(event.titles.faIR + "، ")
            }
            binding.gregorianEvent.text = stringBuilder.toString()

        }
        return binding.root!!
    }

    private fun resetColorDayWeek() {

        binding.saturday.let { txt ->
            txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.blue_200, null))
        }
        binding.sunday.let { txt ->
            txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.blue_200, null))
        }
        binding.monday.let { txt ->
            txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.blue_200, null))
        }
        binding.tuesday.let { txt ->
            txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.blue_200, null))
        }
        binding.wednesday.let { txt ->
            txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.blue_200, null))
        }
        binding.thursday.let { txt ->
            txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.blue_200, null))
        }
        binding.friday.let { txt ->
            txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.blue_200, null))
        }


    }

    private fun setColorDayWeekSelected(day: Int) {
        resetColorDayWeek()
        when (day) {
            0 -> {
                binding.saturday.let { txt ->
                    txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.white, null))
                }
            }
            1 -> {
                binding.sunday.let { txt ->
                    txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.white, null))
                }
            }
            2 -> {
                binding.monday.let { txt ->
                    txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.white, null))
                }
            }
            3 -> {
                binding.tuesday.let { txt ->
                    txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.white, null))
                }
            }
            4 -> {
                binding.wednesday.let { txt ->
                    txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.white, null))
                }
            }
            5 -> {
                binding.thursday.let { txt ->
                    txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.white, null))
                }
            }
            6 -> {
                binding.friday.let { txt ->
                    txt.setTextColor(ResourcesCompat.getColor(txt.resources, R.color.white, null))
                }
            }

        }

    }


}