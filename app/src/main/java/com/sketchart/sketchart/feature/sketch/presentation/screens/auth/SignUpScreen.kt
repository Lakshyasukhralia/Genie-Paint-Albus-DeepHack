package com.sketchart.sketchart.feature.sketch.presentation.screens.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sketchart.sketchart.feature.sketch.presentation.screens.components.InputBox

@Composable
@Destination
fun SignUpScreen(
    navigator: DestinationsNavigator,
    viewModel: UserAuthViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    Column {
        Spacer(modifier = Modifier.height(80.dp))
        InputBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(1.dp, Color.LightGray, CircleShape),
            placeHolder = "Enter your email",
            onTyped = { },
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(1.dp, Color.LightGray, CircleShape),
            placeHolder = "Enter password",
            onTyped = { },
        )
    }
}