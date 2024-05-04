package com.example.nfceditor

import ShowError
import ShowRefreshableError
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nfceditor.tools.NFCState
import com.example.nfceditor.ui.theme.NFCEditorTheme


class MainActivity : ComponentActivity() {
    private val viewModel: NFCMessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUp(this)
        setContent {
            NFCEditorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RouteNFCSupport(viewModel.nfcFactory?.state ?: NFCState.Error) // onResume() is being called at the start, handle this so the dispatch won't consuse energy
                }
            }
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent?.let {
            //get message
            //viewModel.getMessage(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter?.disableForegroundDispatch(this)
        //viewModel.nfcFactory?.disableNFCSearch()
    }

    override fun onResume() {
        super.onResume()
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )
        val intentFilters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null)

        //viewModel.nfcFactory?.enableNFCSearch()
    }
}

@Composable
fun RouteNFCSupport(state: NFCState) {
    when (state) {
        NFCState.NotSupported -> ShowError("Device does not support NFC!")
        NFCState.Disabled -> ShowRefreshableError("NFC is supported, but disabled.")
        NFCState.Enabled -> ProceedToMainMenu()
        NFCState.Error -> ShowError("NFCFactory wasn't declared yet!")
    }
}



