package com.sketchart.sketchart.core.persistence.datastore.proto

import com.sketchart.sketchart.feature.sketch.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AppProtoDataStoreRepository {
    suspend fun getUser(): Flow<User?>
    suspend fun saveUser(user: User)
}