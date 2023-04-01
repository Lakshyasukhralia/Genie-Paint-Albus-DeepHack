package com.sketchart.sketchart.core.persistence.datastore.proto

import android.content.Context
import androidx.datastore.dataStore
import com.sketchart.sketchart.feature.sketch.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val PROTO_DS_NAME = "swipenstay_proto_ds.json"
private val Context.protoDataStore by dataStore(PROTO_DS_NAME, AppProtoSerializer)

class AppProtoDataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : AppProtoDataStoreRepository {

    override suspend fun getUser(): Flow<User?> = flow {
        context.protoDataStore.data.collect() {
            emit(it.user)
        }
    }

    override suspend fun saveUser(user: User) {
        context.protoDataStore.updateData {
            it.copy(user = user)
        }
    }

}