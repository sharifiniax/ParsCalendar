package com.sharifiniax.parscalendar.ui.todo

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.databinding.FragmentTodoBinding
import com.sharifiniax.parscalendar.ui.todo.calendar.CalendarsAdapter
import com.sharifiniax.parscalendar.ui.todo.task.TaskAdapter
import com.sharifiniax.parscalendar.utils.BottomSheetState
import com.sharifiniax.parscalendar.utils.ButtonState
import com.sharifiniax.parscalendar.utils.InsertTaskState
import com.sharifiniax.parscalendar.utils.PriorityMenuState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import javax.inject.Inject

@AndroidEntryPoint
class TodoFragment @Inject constructor() : Fragment() {

    private val FAILED = "خطایی رخ داده مجدد تلاش کن"
    private val SUCCESS = "ثبت شد"

    /*
    max and min peek height
     */
    private val MAXPEEKHEIGHT = 550
    private val MINPEEKHEIGHT = 0
    private val CALMAXPEEKHEIGHT = 900
    private val CALMINPEEKHEIGHT = 0


    private val viewModel: TodoViewModel by viewModels()
    lateinit var binding: FragmentTodoBinding

    private lateinit var bottomSheet: BottomSheetBehavior<View>
    private lateinit var calendarBottomSheet: BottomSheetBehavior<View>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //init binding
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.todoBottomSheet.viewModel = viewModel

        //declare bottom sheets
        bottomSheet = BottomSheetBehavior.from(binding.todoBottomSheet.root)
        calendarBottomSheet = BottomSheetBehavior.from(binding.calendarBottomSheet.root)

        initBottomSheet()
        initCalendarBottomSheet()
        lifecycleScope.launchWhenStarted {
            viewModel.bottomSheetState.collect {

                when (it) {

                    BottomSheetState.Hide -> {
                        bottomSheet.peekHeight = MINPEEKHEIGHT
//                        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
                        binding.floatingActionButton2.visibility = View.VISIBLE

                        closeKeyboard()

                    }

                    BottomSheetState.Collapse -> {
                        bottomSheet.peekHeight = MAXPEEKHEIGHT
//                        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                        binding.floatingActionButton2.visibility = View.INVISIBLE
                        clearText()
                        openKeyboard()
                    }

                    BottomSheetState.Expand -> {

                    }


                }

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.calendarBottomSheetState.collect {

                when (it) {

                    BottomSheetState.Hide -> {
                        calendarBottomSheet.peekHeight = CALMINPEEKHEIGHT
                    }

                    BottomSheetState.Collapse -> {
                        calendarBottomSheet.peekHeight = CALMAXPEEKHEIGHT

                    }

                    BottomSheetState.Expand -> {

                    }


                }

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.sendTaskState.collect {

                when (it) {

                    ButtonState.Enable -> {
                        binding.todoBottomSheet.sendTask.isEnabled = true

                    }
                    ButtonState.Disable -> {
                        binding.todoBottomSheet.sendTask.isEnabled = false

                    }

                }

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.insertTaskState.collect {

                when (it) {

                    InsertTaskState.Failed -> {
                        Snackbar.make(binding.root, FAILED, Snackbar.LENGTH_SHORT).show()


                    }
                    InsertTaskState.Success -> {
                        Snackbar.make(binding.root, SUCCESS, Snackbar.LENGTH_SHORT).show()


                    }

                    InsertTaskState.Empty -> {

                    }

                }

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.menuFlag.collect {

                when(it){

                    PriorityMenuState.Close->{


                    }
                    PriorityMenuState.Open->{

                        showPriorityMenu()
                    }




                }



            }
        }
        val taskAdapter = TaskAdapter(viewModel)
        binding.taskRecycler.layoutManager = LinearLayoutManager(context)
        binding.taskRecycler.adapter = taskAdapter

        viewModel.taskList.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

        viewModel.selectDay.observe(viewLifecycleOwner) {
            binding.todoBottomSheet.taskDay.text = it.toString()
        }
        val adapter = CalendarsAdapter(viewModel)
        binding.calendarBottomSheet.calendarsRecyclerView.layoutManager =
            LinearLayoutManager(context)
        binding
            .calendarBottomSheet
            .calendarsRecyclerView.adapter = adapter

        viewModel.calendarList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


        binding.todoBottomSheet.taskTitle.doAfterTextChanged {
            it.let {
                if (it.toString() != ""
                ) {
                    viewModel.enableSendTask()

                } else {
                    viewModel.disableSendTask()

                }
            }


        }

        return binding.root
    }


    private fun initCalendarBottomSheet() {
        calendarBottomSheet.peekHeight = 0


    }

    private fun initBottomSheet() {

        bottomSheet.isDraggable = false
        calendarBottomSheet.isDraggable = false

    }


    private fun closeKeyboard() {

        val view = activity?.currentFocus
        view?.clearFocus()

        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)


    }


    private fun openKeyboard() {


        if (binding.todoBottomSheet.root.requestFocus()) {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(
                binding.todoBottomSheet.taskTitle,
                InputMethodManager.SHOW_IMPLICIT
            )

        }

    }

    private fun showPriorityMenu() {
        PopupMenu(requireContext(), binding.todoBottomSheet.priorityFlag).apply {
            // MainActivity implements OnMenuItemClickListener
            setOnMenuItemClickListener{
                 when (it.itemId) {
                    R.id.priority_1 -> {
                        viewModel.priority=1
                        return@setOnMenuItemClickListener true
                    }

                    R.id.priority_2 -> {
                        viewModel.priority=2
                        return@setOnMenuItemClickListener true
                    }

                    R.id.priority_3 -> {
                        viewModel.priority=3
                        return@setOnMenuItemClickListener true
                    }

                    R.id.priority_4 -> {
                        viewModel.priority=4
                        return@setOnMenuItemClickListener true
                    }

                     else ->{
                         return@setOnMenuItemClickListener false
                     }
            }

        }
            inflate(R.menu.priority_menu)
            show()
    }



    }



    private fun clearText() {

        binding.todoBottomSheet.taskTitle.setText("")
        binding.todoBottomSheet.taskDescription.setText("")


    }
    fun hideBottomSheetFromOutSide(ev: MotionEvent) {

        if (bottomSheet.peekHeight >= 0
            && calendarBottomSheet.peekHeight <= 0
        ) {
            val outRect = Rect()
            binding.todoBottomSheet.root.getGlobalVisibleRect(outRect)
            if (!outRect.contains(
                    ev.rawX.toInt(),
                    ev.rawY.toInt()
                )
            ) {

                viewModel.closeBottomSheet()

            }

        }
        if (
            calendarBottomSheet.peekHeight >= 0
        ) {
            val outRect = Rect()
            binding.calendarBottomSheet.root.getGlobalVisibleRect(outRect)
            if (!outRect.contains(
                    ev.rawX.toInt(),
                    ev.rawY.toInt()
                )
            ) {

                viewModel.closeCalendarBottomSheet(viewModel.selectDay.value!!)

            }

        }


    }

}
