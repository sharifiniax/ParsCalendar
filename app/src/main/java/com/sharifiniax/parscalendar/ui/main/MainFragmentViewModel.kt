package com.sharifiniax.parscalendar.ui.main

import androidx.lifecycle.*
import com.sharifiniax.parscalendar.model.CalendarRepositoryImpl
import com.sharifiniax.parscalendar.model.DayModel
import com.sharifiniax.parscalendar.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val repositoryImpl: CalendarRepositoryImpl) :
    ViewModel(), EventTitles {


    private val today: MutableLiveData<DayModel> = MutableLiveData(repositoryImpl.today())
    val selectItem: MutableLiveData<DayModel?> = MutableLiveData(today.value)

    private val _jalaliEventTitles =
        MutableLiveData(selectItem.value?.let { repositoryImpl.jalaliEvent(it) })
    val jalaliEventTitles = _jalaliEventTitles


    private val _hijriEventTitles =
        MutableLiveData(selectItem.value?.let { repositoryImpl.hijriEvent(it) })
    val hijriEventTitles = _hijriEventTitles
    private val _gregorianEventTitles =
        MutableLiveData(selectItem.value?.let { repositoryImpl.gregorianEvent(it) })
    val gregorianEventTitles = _gregorianEventTitles


    private var _listMonth = MutableLiveData(repositoryImpl.currentMonth())
    val listMonth = _listMonth
    private var _currentMonth: MutableLiveData<String> =
        MutableLiveData(_listMonth.value?.get(12)?.month?.let { Utils.persianMonth(it) })
    val currentMonth = _currentMonth
    private var _currentYear: MutableLiveData<String> =
        MutableLiveData(_listMonth.value?.get(12)?.year?.let { Utils.convertNumber(it) })
    val currentYear = _currentYear

    override fun previousMonth() {
        val value = repositoryImpl.previousMonth()
        _listMonth.value = value

        _currentMonth.value =
            value[12].month.let { Utils.persianMonth(it) }
        _currentYear.value =
            value[12].year.let { Utils.convertNumber(it) }

        _jalaliEventTitles.value = ArrayList()
        if (value.contains(today.value)) {
            selectItem.value = today.value

        } else {
            selectItem.value = null

        }
    }

    override fun nextMonth() {
        val value = repositoryImpl.nextMonth()
        _listMonth.value = value

        _currentMonth.value =
            value[12].month.let { Utils.persianMonth(it) }
        _currentYear.value =
            value[12].year.let { Utils.convertNumber(it) }
        _jalaliEventTitles.value = ArrayList()
        if (value.contains(today.value)) {
            selectItem.value = today.value

        } else {
            selectItem.value = null

        }
    }


    override fun getJalaliEventTitles(dayModel: DayModel) {
        _jalaliEventTitles.value = repositoryImpl.jalaliEvent(dayModel)
    }

    override fun getHijriEventTitles(dayModel: DayModel) {
        _hijriEventTitles.value = repositoryImpl.hijriEvent(dayModel)
    }

    override fun getGregorianEventTitles(dayModel: DayModel) {
        _gregorianEventTitles.value = repositoryImpl.gregorianEvent(dayModel)
    }

    override fun isJalaliEventExist(dayModel: DayModel): Boolean {
        return repositoryImpl.jalaliEvent(dayModel).isNotEmpty()
    }

    override fun isHijriEventExist(dayModel: DayModel): Boolean {
        return repositoryImpl.hijriEvent(dayModel).isNotEmpty()
    }

    override fun isHoliday(dayModel: DayModel): Boolean {
        return repositoryImpl.isHoliday(dayModel)
    }

    override fun isGregorianEventExist(dayModel: DayModel): Boolean {
        return repositoryImpl.gregorianEvent(dayModel).isNotEmpty()
    }

    fun getGregorianDate(dayModel: DayModel): String {
        return repositoryImpl.getGregorianDate(dayModel)
    }

    fun getHijriDate(dayModel: DayModel): String {
        return repositoryImpl.getHijriDate(dayModel)
    }
     fun gregorianDateAlphabetic(dayModel: DayModel): String {
        return repositoryImpl.gregorianDateAlphabetic(dayModel)
    }

     fun hijriDateAlphabetic(dayModel: DayModel): String {
        return repositoryImpl.hijriDateAlphabetic(dayModel)
    }

}

interface EventTitles {

    fun getJalaliEventTitles(dayModel: DayModel)
    fun getHijriEventTitles(dayModel: DayModel)
    fun getGregorianEventTitles(dayModel: DayModel)

    fun nextMonth()
    fun previousMonth()

    fun isJalaliEventExist(dayModel: DayModel): Boolean
    fun isGregorianEventExist(dayModel: DayModel): Boolean
    fun isHijriEventExist(dayModel: DayModel): Boolean
    fun isHoliday(dayModel:DayModel):Boolean
}

