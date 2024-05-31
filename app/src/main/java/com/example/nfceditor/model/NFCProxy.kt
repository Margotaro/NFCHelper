package com.example.nfceditor.model

import android.app.Activity
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ReaderCallback
import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.Ndef
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.nfceditor.MainActivity
import com.example.nfceditor.model.listeners.NFCManager
import com.example.nfceditor.model.listeners.NFCProxyDataProvider
import com.example.nfceditor.model.listeners.NFCProxyListener
import com.example.nfceditor.model.listeners.NewDialogListener
import com.example.nfceditor.tools.NFCState
import com.example.nfceditor.tools.NFCStateChecker
import com.example.nfceditor.model.listeners.ScanningModeProvider
import com.example.nfceditor.viewmodels.ScanningMode
import java.io.IOException

class NFCProxy(context: MainActivity,
               private val newDialogListener: NewDialogListener,
               var nfcProxyListener: NFCProxyListener,
               var nfcProxyDataProvider: NFCProxyDataProvider
): NFCManager, ReaderCallback, ScanningModeProvider {
    override val readingState: MutableLiveData<ScanningMode> =  MutableLiveData<ScanningMode>(ScanningMode.None)

    companion object {
        private val TAG = "NFCProxy"
    }
    private val adapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)
    val state: NFCState
        get() {
            return NFCStateChecker.getNFCState(adapter)
        }

    private var manager: NFCManager? = null

    init {
        updateManager()
    }

    fun updateNFCState() {
        updateManager()
    }

    private fun updateManager() {
        manager = null
        if (state == NFCState.Enabled) {
            adapter?.let {
                manager = NFCManagerImpl(adapter, this)
            }
        }
    }

    override fun enableNfc(activity: Activity) {
        manager?.enableNfc(activity)
    }
    override fun disableNfc() {
        manager?.disableNfc()
    }

    override fun onTagDiscovered(tag: Tag?) {
        Ndef.get(tag)?.let { ndef ->
            try {
                ndef.cachedNdefMessage.let { message ->
                    val records = message.records.map { it.convertToString() }
                    nfcProxyListener.updateRecords(records)
                }
            } catch (e: NullPointerException) {
                val message = "NFC tag is empty"
                Log.i(TAG, message)
                newDialogListener.showEmptyTagDialog()
            }

            nfcProxyDataProvider.getMessageForNfc()?.let { message ->
                try {
                    ndef.connect()
                    ndef.writeNdefMessage(NdefMessage(message.convertToNdefRecord()))
                } catch (e: TagLostException) {
                    newDialogListener.showLostTagDialog()
                } finally {
                    try {
                        ndef.close()
                    } catch (e: IOException) {
                        Log.e(TAG, "Error closing socket connection", e)
                    }
                }
            }
        }
    }

}
