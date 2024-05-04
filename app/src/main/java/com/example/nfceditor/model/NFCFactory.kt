package com.example.nfceditor.model

import android.app.Activity
import android.nfc.NfcAdapter
import com.example.nfceditor.tools.NFCState
import com.example.nfceditor.tools.NFCStateChecker

class NFCFactory(val context: Activity) {
    private val adapter: NfcAdapter?
    var state: NFCState
    private var manager: NFCManager? = null

    init {
        adapter = NfcAdapter.getDefaultAdapter(context)
        state = NFCStateChecker.getNFCState(adapter)
        updateManager()
    }

    fun updateNFCState() {
        state = NFCStateChecker.getNFCState(adapter)
        updateManager()
    }
    private fun updateManager() {
        var tManager: NFCManager? = null
        if(state == NFCState.Enabled) {
            adapter?.let {
                tManager = NFCManager(adapter = adapter, context = context)
            }
        }
        manager = tManager
    }
    fun enableNFCSearch() {
        manager?.resumeForegroundDispatch()
    }
    fun disableNFCSearch() {
        manager?.pauseForegroundDispatch()
    }
}