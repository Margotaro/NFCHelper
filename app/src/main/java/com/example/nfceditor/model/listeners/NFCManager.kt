package com.example.nfceditor.model.listeners

import android.app.Activity

interface NFCManager {
    fun enableNfc(activity: Activity)
    fun disableNfc()
}