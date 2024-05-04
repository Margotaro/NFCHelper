package com.example.nfceditor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfceditor.model.NFCFactory
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProceedToMainMenu() {
    val viewModel: NFCMessageViewModel = viewModel()
    val message by viewModel.message.observeAsState("Read the tag")
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
            var sendTagFormVisibility by remember {
                mutableStateOf(false)
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = buttonShape,
                onClick = {
                    sendTagFormVisibility = !sendTagFormVisibility
                }
            ) {
                Box(
                    modifier = Modifier
                        .height(64.dp)
                        .width(128.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Send to the tag",
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
            HideableComposable({ WriteTag() }, sendTagFormVisibility)


            //          READ TAG BLOCK
            var readTagNumberVisibility by remember {
                mutableStateOf(false)
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = buttonShape,
                onClick = {
                    readTagNumberVisibility = !readTagNumberVisibility
                    //viewModel.scanForNFC()
                }
            ) {
                Box(
                    modifier = Modifier
                        .height(64.dp)
                        .width(128.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
            HideableComposable({ ReadTag(viewModel) }, readTagNumberVisibility)
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

@Preview(showBackground = true)
@Composable
fun WriteTag() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val messageForNFC = remember { mutableStateOf("") }
            TextField("Write message for the tag", messageForNFC)
            Button(onClick = { }) {
                Text("Send to the tag")
            }
        }
    }
}

@Composable
fun ReadTag(viewModel: NFCMessageViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val message = MutableLiveData<String>()
        Column(
            modifier = Modifier.padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val messageForNFC = remember { mutableStateOf("") }
            Text(message.value ?: "New Message")
            Button(onClick = {
                message.value = viewModel.message.value
            }) {
                Text("Get the tag number")
            }
        }
    }
}

@Composable
fun HideableComposable(contentComposable: @Composable () -> Unit, visible: Boolean) {
    AnimatedVisibility(visible) {
        contentComposable()
    }
}