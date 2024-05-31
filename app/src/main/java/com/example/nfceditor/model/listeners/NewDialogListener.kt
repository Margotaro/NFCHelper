package com.example.nfceditor.model.listeners

import androidx.compose.runtime.Composable

interface NewDialogListener {
    fun showEmptyTagDialog()
    fun showLostTagDialog()
    fun showSearchingTagDialog()
}