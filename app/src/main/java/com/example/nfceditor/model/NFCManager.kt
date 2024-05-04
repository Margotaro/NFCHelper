package com.example.nfceditor.model

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.util.Log

class NFCManager(private val context: Activity, val adapter: NfcAdapter) {
    private val pendingIntent: PendingIntent
    private val filters: Array<IntentFilter>
    private val techList: Array<Array<String>>

    init {
        val intent = Intent(context, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        val intentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
                addCategory(Intent.CATEGORY_DEFAULT)
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Check your MIME type.", e)
            }
        }
        filters = arrayOf(intentFilter)
        techList = arrayOf()
    }

    fun resumeForegroundDispatch() {
        adapter.enableForegroundDispatch(context, pendingIntent, filters, techList) //investigate here
    }

    fun pauseForegroundDispatch() {
        adapter.disableForegroundDispatch(context)
    }

    fun readMessage(intent: Intent): String? {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                ?.also { rawMessages ->
                    val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                    // Process the messages array.
                    Log.i("info", messages[0].toString())
                    return messages[0].toString()
                }
            return null
        } else {
            return null
        }
    }
}