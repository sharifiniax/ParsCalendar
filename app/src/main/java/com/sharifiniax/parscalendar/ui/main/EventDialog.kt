package com.sharifiniax.parscalendar.ui.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.databinding.EventDialogBinding
import com.sharifiniax.parscalendar.model.DayModel

class EventDialog(context: Context,private val day:DayModel) : Dialog(context) {

    lateinit var binding: EventDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.event_dialog,null,false)
        binding.day=day

        setContentView(binding.root)
    }





}