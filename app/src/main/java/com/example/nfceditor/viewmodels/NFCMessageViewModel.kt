package com.example.nfceditor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nfceditor.model.listeners.NFCProxyListener
import com.example.nfceditor.model.listeners.NFCProxyDataProvider
import com.example.nfceditor.model.listeners.ScanningModeListener
import com.example.nfceditor.model.listeners.ScanningModeProvider


enum class ScanningMode { Writing, Reading, None }
class NFCMessageViewModel : ViewModel(), NFCProxyListener, NFCProxyDataProvider, ScanningModeListener {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _draftMessage = MutableLiveData<String>()
    val draftMessage: LiveData<String> = _draftMessage

    val scanningModeProvider: ScanningModeProvider? = null

    override fun updateRecords(records: List<String>) {
        for (record in records) {
            _message.postValue(record)
        }
    }

    override fun getMessageForNfc(): String? {
        return draftMessage.value
    }

    fun setMessageForNfc(message: String) {
        _draftMessage.value = message
    }

    override fun scanningModeHasChanged(mode: ScanningMode) {
        scanningModeProvider?.readingState?.value = mode
    }
}