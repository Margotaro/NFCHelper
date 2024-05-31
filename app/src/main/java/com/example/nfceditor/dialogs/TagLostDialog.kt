package com.example.nfceditor.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun LostTagAlertDialog(showDialog: MutableState<Boolean>) {
    AlertDialog(text = "You moved your phone too fast. Try again, please.", showDialog = showDialog)
}