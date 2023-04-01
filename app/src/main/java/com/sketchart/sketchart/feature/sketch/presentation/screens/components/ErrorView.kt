package com.sketchart.sketchart.feature.sketch.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(error: String) {

    val err by remember { mutableStateOf(error) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Red)
            .padding(4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = err,
            color = Color.White,
        )
    }

}