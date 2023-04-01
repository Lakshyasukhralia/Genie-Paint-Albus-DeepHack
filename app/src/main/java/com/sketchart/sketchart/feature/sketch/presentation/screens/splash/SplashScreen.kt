package com.sketchart.sketchart.feature.sketch.presentation.screens.splash

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sketchart.sketchart.feature.sketch.presentation.screens.auth.UserAuthViewModel
import com.sketchart.sketchart.feature.sketch.presentation.screens.components.InputBox

@Composable
@Destination
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: UserAuthViewModel = hiltViewModel()
) {
    Column {
        InputBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(1.dp, Color.LightGray, CircleShape),
            onTyped = { },
        )
    }
}