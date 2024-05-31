package com.example.nfceditor.model.listeners

import androidx.lifecycle.MutableLiveData
import com.example.nfceditor.viewmodels.ScanningMode

interface ScanningModeProvider {

    val readingState: MutableLiveData<ScanningMode>
}