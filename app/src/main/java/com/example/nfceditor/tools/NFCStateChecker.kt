package com.example.nfceditor.tools

import android.content.Context
import android.nfc.NfcAdapter

enum class NFCState { NotSupported, Disabled, Enabled, Error}
class NFCStateChecker {
    companion object {
        fun getNFCState(nfcAdapter: NfcAdapter?): NFCState {
            if (nfcAdapter == null) {
                return NFCState.NotSupported
            } else if (!nfcAdapter.isEnabled) {
                return NFCState.Disabled
            }
            return NFCState.Enabled
        }
    }
}