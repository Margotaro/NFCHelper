package com.example.nfceditor

import com.example.nfceditor.screens.ShowError
import com.example.nfceditor.screens.ShowRefreshableError
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.nfceditor.dialogs.EmptyTagAlertDialog
import com.example.nfceditor.dialogs.LostTagAlertDialog
import com.example.nfceditor.dialogs.SearchingTagAlertDialog
import com.example.nfceditor.model.NFCProxy
import com.example.nfceditor.model.listeners.NewDialogListener
import com.example.nfceditor.screens.ProceedToMainMenu
import com.example.nfceditor.tools.NFCState
import com.example.nfceditor.ui.theme.NFCEditorTheme
import com.example.nfceditor.viewmodels.NFCMessageViewModel


class MainActivity : ComponentActivity(), NewDialogListener {
    private var nfcProxy: NFCProxy? = null

    private val showEmptyTagDialogState = mutableStateOf(false)
    private val showLostTagDialogState = mutableStateOf(false)
    private val searchingTagDialogState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFCEditorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: NFCMessageViewModel by viewModels()
                    val nfcProxy = NFCProxy(
                        context = this,
                        newDialogListener = this,
                        nfcProxyListener = viewModel,
                        nfcProxyDataProvider = viewModel)
                    this.nfcProxy = nfcProxy

                    nfcProxy.enableNfc(this)
                    setUpDialogs(
                        showEmptyTagDialogState,
                        showLostTagDialogState,
                        searchingTagDialogState)
                    RouteNFCSupport(nfcProxy) // onResume() is being called at the start, handle this so the dispatch won't consume energy
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcProxy?.enableNfc(this)
    }
    override fun onPause() {
        super.onPause()
        nfcProxy?.disableNfc()
    }

    override fun showEmptyTagDialog() {
        showEmptyTagDialogState.value = true
    }

    override fun showLostTagDialog() {
        showLostTagDialogState.value = true
    }

    override fun showSearchingTagDialog() {
        searchingTagDialogState.value = true
    }
}

@Composable
fun RouteNFCSupport(nfc: NFCProxy?) {
    when (nfc?.state) {
        NFCState.NotSupported -> ShowError("Device does not support NFC!")
        NFCState.Disabled -> ShowRefreshableError("NFC is supported, but disabled.", nfc)
        NFCState.Enabled -> ProceedToMainMenu()
        else -> ShowError("NFCProxy wasn't declared yet!")
    }
}

@Composable
fun setUpDialogs(
    showEmptyTagDialogState: MutableState<Boolean>,
    showLostTagDialogState: MutableState<Boolean>,
    searchingTagDialogState: MutableState<Boolean>
) {
    EmptyTagAlertDialog(showEmptyTagDialogState)
    LostTagAlertDialog(showLostTagDialogState)
    SearchingTagAlertDialog(searchingTagDialogState)
}



