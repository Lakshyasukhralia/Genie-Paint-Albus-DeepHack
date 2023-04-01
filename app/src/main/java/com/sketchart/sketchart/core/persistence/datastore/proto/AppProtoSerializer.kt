package com.sketchart.sketchart.core.persistence.datastore.proto

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object AppProtoSerializer : Serializer<AppProtoModel> {

    override val defaultValue: AppProtoModel
        get() = AppProtoModel()

    override suspend fun readFrom(input: InputStream): AppProtoModel {
        return try {
            Json.decodeFromString(
                deserializer = AppProtoModel.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppProtoModel, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AppProtoModel.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}