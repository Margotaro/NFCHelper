package com.example.nfceditor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nfceditor.model.NFCFactory

class NFCMessageViewModel : ViewModel()  {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    var nfcFactory: NFCFactory? = null

    fun setUp(activity: Activity) {
        nfcFactory = NFCFactory(activity)
    }

    fun setMessage(newMessage: String) {
        _message.value = newMessage
        //broadcast it somewhere
    }

    fun getMessage(intent: Intent): String {
        _message.value = intent.data.toString()
        return message.value ?: "123456"//_message.value = newMessage
    }

    fun scanForNFC() {
        nfcFactory?.enableNFCSearch()
    }
}