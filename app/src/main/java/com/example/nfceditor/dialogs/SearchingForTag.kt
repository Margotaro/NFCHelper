package com.example.nfceditor.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun SearchingTagAlertDialog(showDialog: MutableState<Boolean>) {
    AlertDialog(text = "Searching for the tag.", showDialog = showDialog)
}