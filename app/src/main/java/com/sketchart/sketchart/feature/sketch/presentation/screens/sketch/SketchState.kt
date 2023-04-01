package com.sketchart.sketchart.feature.sketch.presentation.screens.sketch

data class SketchState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isSuccess: Boolean = false,
)
