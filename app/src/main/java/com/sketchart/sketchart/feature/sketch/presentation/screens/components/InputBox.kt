package com.sketchart.sketchart.feature.sketch.presentation.screens.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun InputBox(
    modifier: Modifier = Modifier,
    placeHolder: String = "Enter email",
    onTyped: (String) -> Unit = {},
) {
    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        textStyle = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
        onValueChange = {
            onTyped(it)
            text = it
        },
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp
            )
        },
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}
