package com.sketchart.sketchart.feature.sketch.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SketchItem(
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val id: String? = null,
    var photo: String? = null,
    var subject: String? = null,
    var artist_id: String? = null,
) : Parcelable