package com.sketchart.sketchart.feature.sketch.presentation.screens.sketchgenerate

import com.sketchart.sketchart.feature.sketch.domain.models.Sketch

data class SketchGenerateState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isSuccess: Boolean = false,
    val sketchList: List<Sketch>? = emptyList(),
    val generatedSketchList: List<String>? = emptyList()
)
