package com.example.nfceditor.model.listeners

import androidx.lifecycle.MutableLiveData
import com.example.nfceditor.viewmodels.ScanningMode

interface ScanningModeListener {
    fun scanningModeHasChanged(mode: ScanningMode)
}