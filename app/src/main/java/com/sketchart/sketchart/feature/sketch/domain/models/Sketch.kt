package com.sketchart.sketchart.feature.sketch.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sketch(
    var artistId: String? = null,
    val photo: String? = null,
    val subject: String? = null,
) : Parcelable