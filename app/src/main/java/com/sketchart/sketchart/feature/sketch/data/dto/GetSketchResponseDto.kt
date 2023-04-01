package com.sketchart.sketchart.feature.sketch.data.dto

import com.google.gson.annotations.SerializedName

data class GetSketchResponseDto(
    @SerializedName("items")
    val items: List<SketchItem>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("perPage")
    val perPage: Int,
    @SerializedName("totalItems")
    val totalItems: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)