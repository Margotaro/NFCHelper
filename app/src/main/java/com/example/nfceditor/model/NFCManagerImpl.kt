package com.example.nfceditor.model

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ReaderCallback
import android.os.Bundle
import com.example.nfceditor.model.listeners.NFCManager
import com.example.nfceditor.model.listeners.NFCProxyDataProvider
import com.example.nfceditor.model.listeners.NFCProxyListener

class NFCManagerImpl(private val adapter: NfcAdapter, private val callback: ReaderCallback):
    NFCManager {
    private var _activity: Activity? = null

    override fun enableNfc(activity: Activity) {
        _activity = activity
        val options = Bundle()
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)

        // Ask for all type of cards to be sent to the App as they all might contain NDEF data
        adapter.enableReaderMode(
            activity,
            callback,
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
            options
        )
    }
    override fun disableNfc() {
        _activity?.let {
            adapter.disableReaderMode(_activity)
        }
    }

}