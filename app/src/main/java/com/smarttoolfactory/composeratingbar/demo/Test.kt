package com.smarttoolfactory.composeratingbar.demo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun Test() {
    Column(
        Modifier.fillMaxSize()
    ) {
        var value by remember {
            mutableStateOf(0f)
        }

        Slider(
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, Color.Red),
            value = value,
            onValueChange = { value = it }
        )
    }
}