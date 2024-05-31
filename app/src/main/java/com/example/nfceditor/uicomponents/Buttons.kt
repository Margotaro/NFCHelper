package com.example.nfceditor.uicomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfceditor.screens.WriteTag
import kotlinx.coroutines.delay

val buttonShape = RoundedCornerShape(4.dp)

@Preview
@Composable
fun ExpandableMainScreenButton(
    name: String = "Default name",
    contents: @Composable () -> Unit = { WriteTag() },
    action: () -> Unit = { }
) {
    Column {
        var visibility by remember {
            mutableStateOf(false)
        }
        val rotated = remember {
            mutableStateOf(false)
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = buttonShape,
            onClick = {
                visibility = !visibility
                rotated.value = !rotated.value
                action()
            }
        ) {
            Box(
                modifier = Modifier
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = name,
                        style = TextStyle(fontSize = 18.sp)
                    )

                    ArrowIcon(rotated = rotated)
                }
            }
        }
        HideableComposable(contents, visibility)
    }
}
@Composable
fun ArrowIcon(rotated: MutableState<Boolean>) {
    val rotationAngle by animateFloatAsState(if (rotated.value) 90f else 0f)
    Box(
        modifier = Modifier.graphicsLayer {
            rotationZ = rotationAngle
        }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Arrow Left",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun HideableComposable(contentComposable: @Composable () -> Unit, visible: Boolean) {
    AnimatedVisibility(visible) {
        val shadowRadius = 1.dp
        val cornerRadius = 1.dp
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .shadow(
                    elevation = shadowRadius,
                    shape = RoundedCornerShape(cornerRadius),
                    clip = false
                )
        ) { contentComposable() }
    }
}