package com.sharifiniax.parscalendar.ui.todo

import androidx.lifecycle.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.data.DayModel
import com.sharifiniax.parscalendar.data.Task
import com.sharifiniax.parscalendar.model.CalendarRepositoryImpl
import com.sharifiniax.parscalendar.repository.TodoRepository
import com.sharifiniax.parscalendar.repository.TodoRepositoryImpl
import com.sharifiniax.parscalendar.utils.BottomSheetState
import com.sharifiniax.parscalendar.utils.ButtonState
import com.sharifiniax.parscalendar.utils.DayTask
import com.sharifiniax.parscalendar.utils.InsertTaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val calendarRepository: CalendarRepositoryImpl
) : ViewModel(),ICloseCalendarBottomSheet {

    var description:String=""
    var title:String=""

    private var _calendarBottomSheetState: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.Hide)
    val calendarBottomSheetState: StateFlow<BottomSheetState> = _calendarBottomSheetState
    private val _bottomSheetState: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.Hide)
    val bottomSheetState: StateFlow<BottomSheetState> = _bottomSheetState

    private val _selectDay : MutableLiveData<DayModel> = MutableLiveData(calendarRepository.today())
    val selectDay:LiveData<DayModel> = _selectDay

    private val _sendTaskState= MutableStateFlow<ButtonState>(ButtonState.Disable)
    val sendTaskState:StateFlow<ButtonState> =_sendTaskState

    private val _insertTaskState= MutableStateFlow<InsertTaskState>(InsertTaskState.Empty)
    val insertTaskState:StateFlow<InsertTaskState> =_insertTaskState



    fun enableSendTask(){
        _sendTaskState.value=ButtonState.Enable

    }
    fun disableSendTask(){
        _sendTaskState.value=ButtonState.Disable

    }
    private val _calendarListState = liveData<List<List<DayModel>>> {
        val list:MutableList<List<DayModel>> = mutableListOf()
        list.add(calendarRepository.currentMonth())
        for (i in 1..24){
            list.add(calendarRepository.nextMonth())
        }
        emit(list)
    }
//
    val calendarListState: LiveData<List<List<DayModel>>> =_calendarListState



    fun openBottomSheet() {

        _bottomSheetState.value = BottomSheetState.Collapse

    }

    fun closeBottomSheet() {

        _bottomSheetState.value = BottomSheetState.Hide
    }

 fun openCalendarBottomSheet() {

     _calendarBottomSheetState.value = BottomSheetState.Collapse

    }

    override fun closeCalendarBottomSheet(day:DayModel) {
        _calendarBottomSheetState.value = BottomSheetState.Hide
        _selectDay.value = day

    }

    fun insertTask(){

        val task= Task(title,description,_selectDay.value!!,1)

        viewModelScope.launch {
            kotlin.runCatching {
                repository.insert(task)
            }.onFailure {
                _insertTaskState.value=InsertTaskState.Failed

            }.onSuccess {
                _insertTaskState.value=InsertTaskState.Success
                _bottomSheetState.value=BottomSheetState.Hide
                _insertTaskState.value=InsertTaskState.Empty
                _selectDay.value =calendarRepository.today()
                clearText()
            }
        }

    }
    private fun clearText(){
        description=""
        title=""
    }

}

 interface ICloseCalendarBottomSheet {
    fun closeCalendarBottomSheet(day:DayModel)

 }

