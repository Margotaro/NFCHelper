package com.example.nfceditor.model.listeners

interface NFCProxyListener {
    fun updateRecords(records: List<String>)
}
interface NFCProxyDataProvider {
    fun getMessageForNfc(): String?
}