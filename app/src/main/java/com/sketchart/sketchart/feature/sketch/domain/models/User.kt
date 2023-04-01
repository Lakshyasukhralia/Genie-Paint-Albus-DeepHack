package com.sketchart.sketchart.feature.sketch.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@kotlinx.serialization.Serializable
data class User(
    val avatar: String? = null,
    val id: String? = null,
    val name: String? = null,
    val username: String? = null,
    val verified: Boolean? = false,
    val token: String? = null
) : Parcelable
