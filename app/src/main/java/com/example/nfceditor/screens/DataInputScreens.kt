package com.example.nfceditor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nfceditor.viewmodels.NFCMessageViewModel
import com.example.nfceditor.uicomponents.ExpandableMainScreenButton
import com.example.nfceditor.viewmodels.ScanningMode


@Composable
fun WriteTag() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val viewModel: NFCMessageViewModel = viewModel()
        Column(
            modifier = Modifier.padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val messageForNFC = remember { mutableStateOf("") }
            TextField("Write message for the tag", messageForNFC)
            Button(onClick = {
                viewModel.setMessageForNfc(messageForNFC.value)
            }) {
                Text("Send to the tag")
            }
        }
    }
}

@Composable
fun ReadTag(viewModel: NFCMessageViewModel) {
    Box(
        modifier = Modifier
            .wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val message by viewModel.message.observeAsState("New Message")
            Text(text = message)
            Button(onClick = {

            }) {
                Text("Get the tag number")
            }
        }
    }
}

@Composable
fun ProceedToMainMenu() {
    val viewModel: NFCMessageViewModel = viewModel()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                style = TextStyle(fontSize = 45.sp),
                text = "WELCOME"
            )
            val buttonShape = RoundedCornerShape(4.dp)

            //          SEND TAG BLOCK
            ExpandableMainScreenButton(
                name = "Write to the tag",
                contents = { WriteTag() },
                action = {
                    viewModel.scanningModeHasChanged(ScanningMode.Writing)
                }
            )

            //          READ TAG BLOCK
            ExpandableMainScreenButton(
                name = "Read the tag",
                contents = { ReadTag(viewModel = viewModel) },
                action = {
                    viewModel.scanningModeHasChanged(ScanningMode.Reading)
                }
            )
        }
    }
}

@Composable
fun TextField(labelMessage: String, text: MutableState<String>) {
    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(labelMessage) }
    )
}
