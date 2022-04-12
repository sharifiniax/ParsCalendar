package com.sharifiniax.parscalendar.ui.todo

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.ResultReceiver
import android.service.autofill.FieldClassification
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.databinding.FragmentTodoBinding
import com.sharifiniax.parscalendar.ui.todo.calendar.CalendarsAdapter
import com.sharifiniax.parscalendar.utils.BottomSheetState
import com.sharifiniax.parscalendar.utils.ButtonState
import com.sharifiniax.parscalendar.utils.DayTask
import com.sharifiniax.parscalendar.utils.InsertTaskState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
                        Snackbar.make(binding.root,FAILED,Snackbar.LENGTH_SHORT).show()
                    }
                    InsertTaskState.Success -> {
                        Snackbar.make(binding.root,SUCCESS,Snackbar.LENGTH_SHORT).show()
                        clearText()
                    }

                    InsertTaskState.Empty -> {
                        clearText()
                    }

                }

            }
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

        viewModel.calendarListState.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.todoBottomSheet.taskTitle.doAfterTextChanged {
            it.let {
                if (it.toString() != ""
                ) {
                    viewModel.enableSendTask()

                }else
                {
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

    fun hideBottomSheetFromOutSide(ev: MotionEvent) {

        if (bottomSheet
                .state != BottomSheetBehavior.STATE_HIDDEN
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
    }
    fun clearText(){
        binding.todoBottomSheet.taskTitle.setText("")
        binding.todoBottomSheet.taskDescription.setText("")
    }


}