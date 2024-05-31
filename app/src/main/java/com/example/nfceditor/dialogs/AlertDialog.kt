package com.example.nfceditor.dialogs

import androidx.compose.foundation.border
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfceditor.ui.theme.NFCEditorTheme

@Composable
fun AlertDialog(text: String, showDialog: MutableState<Boolean>) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            text = {
                Text(text)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
@Composable
fun EmptyTagAlertDialog(showDialog: MutableState<Boolean>) {
    AlertDialog(text = "Your tag is empty. Write the tag, please.", showDialog = showDialog)
}

@Composable
fun EmptyTagAlertDialogTest() {
        AlertDialog(
            onDismissRequest = { },
            text = {
                Text("Your tag is empty. Write the tag, please.")
            },
            confirmButton = {
                TextButton(
                    onClick = {

                    }
                ) {
                    Text("OK")
                }
            },
            shape = RectangleShape,
            modifier = Modifier.border(width = 4.dp, color = Color.Black)
        )
}

@Preview(showBackground = true)
@Composable
fun ShowDialogExamplePreview() {
    NFCEditorTheme {
        EmptyTagAlertDialogTest()
    }
}