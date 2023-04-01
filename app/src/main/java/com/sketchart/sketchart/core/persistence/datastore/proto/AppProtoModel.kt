package com.sketchart.sketchart.core.persistence.datastore.proto

import com.sketchart.sketchart.feature.sketch.domain.models.User
import kotlinx.serialization.Serializable

@Serializable
data class AppProtoModel(
    val user: User? = null
)
