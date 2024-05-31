package com.example.nfceditor.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nfceditor.viewmodels.NFCMessageViewModel
import com.example.nfceditor.model.NFCProxy
import com.example.nfceditor.tools.NFCState
import kotlinx.coroutines.launch

@Composable
fun ShowError(message: String = "") {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowRefreshableError(message: String = "", nfc: NFCProxy) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var isNfcAvailable by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    fun refresh() = refreshScope.launch {
        refreshing = true
        nfc.updateNFCState()
        isNfcAvailable = nfc.state == NFCState.Enabled
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    if(isNfcAvailable) {
        Box(Modifier.pullRefresh(state)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    style = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp)
                )
            }
            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
        }
    } else {
        ProceedToMainMenu()
    }
}