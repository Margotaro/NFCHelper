package com.example.nfceditor.model

import android.nfc.NdefRecord
import kotlin.experimental.and

fun NdefRecord.convertToString(): String {
        val payload = this.payload
        val textEncoding = if(payload[0] and 128.toByte() == 0.toByte()) "UTF-8" else "UTF-16"
        val langCodeLength = payload[0] and 63.toByte()
       return String(
            payload,
            langCodeLength + 1,
            payload.count() - langCodeLength - 1,
            charset(textEncoding))
}



fun String.convertToNdefRecord(): NdefRecord {
    return NdefRecord.createTextRecord("en", this)
}
