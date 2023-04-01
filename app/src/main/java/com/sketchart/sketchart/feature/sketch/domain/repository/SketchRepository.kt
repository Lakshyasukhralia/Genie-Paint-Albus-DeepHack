package com.sketchart.sketchart.feature.sketch.domain.repository

import com.sketchart.sketchart.core.util.Resource
import com.sketchart.sketchart.feature.sketch.domain.models.Sketch
import com.sketchart.sketchart.feature.sketch.domain.models.User
import kotlinx.coroutines.flow.Flow

interface SketchRepository {

    suspend fun createUser(
        email: String? = null,
        username: String? = null,
        password: String,
        passwordConfirm: String,
        name: String? = null
    ): Flow<Resource<User>>

    suspend fun authenticateUser(identity: String, password: String): Flow<Resource<User>>

    suspend fun createSketch(sketch: Sketch): Flow<Resource<Void>>

    suspend fun getSketches(
        filter: String?,
        sort: String? = "-created"
    ): Flow<Resource<List<Sketch>>>

    suspend fun generateSketch(
        url: String,
        subject: String = ""
    ): Flow<Resource<List<String>>>
}