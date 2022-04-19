package com.sharifiniax.parscalendar.ui.todo

import android.service.controls.Control
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.data.DayModel
import com.sharifiniax.parscalendar.data.Task
import com.sharifiniax.parscalendar.data.TodoCategory
import com.sharifiniax.parscalendar.model.CalendarRepositoryImpl
import com.sharifiniax.parscalendar.repository.CategoryRepositoryImpl
import com.sharifiniax.parscalendar.repository.TodoRepository
import com.sharifiniax.parscalendar.repository.TodoRepositoryImpl
import com.sharifiniax.parscalendar.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val categoryRepository: CategoryRepositoryImpl,
    private val calendarRepository: CalendarRepositoryImpl
) : ViewModel(), ICloseCalendarBottomSheet, TaskAction {

    var description: String = ""
    var title: String = ""
    var category : Int = 1
    var priority: Int = 1

    private var _calendarBottomSheetState: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.Hide)
    val calendarBottomSheetState: StateFlow<BottomSheetState> = _calendarBottomSheetState
    private val _bottomSheetState: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.Hide)
    val bottomSheetState: StateFlow<BottomSheetState> = _bottomSheetState

    val selectDay: MutableLiveData<DayModel> = MutableLiveData(calendarRepository.today())
    override val today: LiveData<DayModel> = selectDay

    private val _selectInbox: MutableLiveData<TodoCategory> = MutableLiveData()
    val selectInbox: LiveData<TodoCategory> = _selectInbox

    private val _sendTaskState = MutableStateFlow<ButtonState>(ButtonState.Disable)
    val sendTaskState: StateFlow<ButtonState> = _sendTaskState

    private val _insertTaskState = MutableStateFlow<InsertTaskState>(InsertTaskState.Empty)
    val insertTaskState: StateFlow<InsertTaskState> = _insertTaskState

    val taskList= repository.getAllNoDone().asLiveData()

    private val _menuFlag: MutableStateFlow<PriorityMenuState> =
        MutableStateFlow(PriorityMenuState.Close)
    val menuFlag: StateFlow<PriorityMenuState> = _menuFlag
    private val _menuInbox: MutableStateFlow<PriorityMenuState> =
        MutableStateFlow(PriorityMenuState.Close)
    val menuInbox: StateFlow<PriorityMenuState> = _menuInbox


    fun enableSendTask() {
        _sendTaskState.value = ButtonState.Enable

    }

    fun disableSendTask() {
        _sendTaskState.value = ButtonState.Disable

    }

    val calendarList = liveData<List<List<DayModel>>> {
        val list: MutableList<List<DayModel>> = mutableListOf()
        list.add(calendarRepository.currentMonth())
        for (i in 1..24) {
            list.add(calendarRepository.nextMonth())
        }
        emit(list)
    }


    fun openBottomSheet() {

        _bottomSheetState.value = BottomSheetState.Collapse

    }

    fun closeBottomSheet() {

        _bottomSheetState.value = BottomSheetState.Hide
    }

    fun openCalendarBottomSheet() {

        _calendarBottomSheetState.value = BottomSheetState.Collapse

    }

    override fun closeCalendarBottomSheet(day: DayModel) {

        _calendarBottomSheetState.value = BottomSheetState.Hide
        selectDay.value = day

    }

    fun insertTask() {
        var cat = TodoCategory()

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                val task = Task(title, description, selectDay.value!!, category, null, priority, done = false)
                repository.insert(task)
            }.onFailure {
                _insertTaskState.value = InsertTaskState.Failed
            }.onSuccess {
                clearTitleAndDescription()

                _insertTaskState.value = InsertTaskState.Success

                _insertTaskState.value = InsertTaskState.Empty
                selectDay.postValue(calendarRepository.today())

                _bottomSheetState.value = BottomSheetState.Hide
            }
        }

    }

    private fun clearTitleAndDescription() {
        title = ""
        description = ""
    }


    override fun deleteItem(it: Task) {

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.delete(it)
            }

        }

    }

    override fun changeTask(it: Task) {
        it.done = !it.done

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(it)
        }


    }

    fun openPriority() {

        _menuFlag.value = PriorityMenuState.Open
        _menuFlag.value = PriorityMenuState.Close

    }
    fun openInbox() {

        _menuInbox.value = PriorityMenuState.Open
        _menuInbox.value = PriorityMenuState.Close

    }
    fun selectCategory(name:String){
        viewModelScope.launch(Dispatchers.IO) {
            val cat=categoryRepository.contain(name)
            if (cat != null){
                cat?.let {
                    category=it.categoryId
                    _selectInbox.postValue(it)
                }
            }else{
                var newCategory=TodoCategory(name)
                categoryRepository.insert(newCategory)
                newCategory= categoryRepository.getId(name)!!

                category=newCategory.categoryId
                _selectInbox.postValue(newCategory)
            }

        }

    }
}

interface ICloseCalendarBottomSheet {
    fun closeCalendarBottomSheet(day: DayModel)
    val today: LiveData<DayModel>
}

interface TaskAction {
    fun deleteItem(it: Task)
    fun changeTask(it: Task)
}

