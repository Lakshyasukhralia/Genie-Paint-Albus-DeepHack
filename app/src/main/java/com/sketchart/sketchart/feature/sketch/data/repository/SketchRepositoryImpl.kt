package com.sketchart.sketchart.feature.sketch.data.repository

import android.app.Application
import androidx.core.net.toUri
import com.sketchart.sketchart.core.helper.getImageBody
import com.sketchart.sketchart.core.util.Resource
import com.sketchart.sketchart.core.util.ResponseHandler
import com.sketchart.sketchart.feature.sketch.data.api.SketchApi
import com.sketchart.sketchart.feature.sketch.data.dto.AuthenticateRequestDto
import com.sketchart.sketchart.feature.sketch.data.dto.CreateUserRequestDto
import com.sketchart.sketchart.feature.sketch.data.dto.GenerateSketchRequestDto
import com.sketchart.sketchart.feature.sketch.data.dto.toUser
import com.sketchart.sketchart.feature.sketch.domain.models.Sketch
import com.sketchart.sketchart.feature.sketch.domain.models.User
import com.sketchart.sketchart.feature.sketch.domain.repository.SketchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SketchRepositoryImpl @Inject constructor(
    private val sketchApi: SketchApi,
    private val app: Application
) :
    SketchRepository, ResponseHandler() {

    override suspend fun createUser(
        email: String?,
        username: String?,
        password: String,
        passwordConfirm: String,
        name: String?
    ): Flow<Resource<User>> = flow {
        safeApiCall {
            sketchApi.createUser(
                CreateUserRequestDto(
                    email,
                    username,
                    password,
                    passwordConfirm,
                    name
                )
            )
        }.collect {
            when (it) {
                is Resource.Success -> emit(Resource.Success(it.data?.toUser()))
                is Resource.Error -> emit(
                    Resource.Error(
                        message = it.message ?: "Something went wrong!", statusCode = it.statusCode
                    )
                )

                is Resource.Loading -> emit(Resource.Loading(it.isLoading))
            }
        }
    }

    override suspend fun authenticateUser(
        identity: String,
        password: String
    ): Flow<Resource<User>> = flow {
        safeApiCall {
            sketchApi.authenticate(
                AuthenticateRequestDto(
                    identity,
                    password
                )
            )
        }.collect {
            when (it) {
                is Resource.Success -> emit(Resource.Success(it.data?.toUser()))
                is Resource.Error -> emit(
                    Resource.Error(
                        message = it.message ?: "Something went wrong!",
                        statusCode = it.statusCode
                    )
                )

                is Resource.Loading -> emit(Resource.Loading(it.isLoading))
            }
        }
    }

    override suspend fun createSketch(sketch: Sketch): Flow<Resource<Void>> = flow {
        safeApiCall {

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart("artist_id", sketch.artistId!!)
            builder.addFormDataPart("subject", sketch.subject!!)

            val file = getImageBody(app, sketch.photo!!.toUri(), "sketch.jpg")
            builder.addFormDataPart(
                "photo",
                file.name,
                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
            val requestBody = builder.build()

            sketchApi.createSketch(requestBody)
        }.collect { res ->
            when (res) {
                is Resource.Success -> emit(Resource.Success(res.data))
                is Resource.Error -> emit(
                    Resource.Error(
                        message = res.message ?: "Something went wrong!",
                        statusCode = res.statusCode
                    )
                )

                is Resource.Loading -> emit(Resource.Loading(res.isLoading))
            }
        }
    }

    override suspend fun getSketches(
        filter: String?,
        sort: String?
    ): Flow<Resource<List<Sketch>>> = flow {
        safeApiCall {
            sketchApi.getSketch(filter, sort)
        }.collect { res ->
            when (res) {
                is Resource.Success -> emit(Resource.Success(res.data?.items?.map {
                    Sketch(
                        it.artist_id,
                        "${SketchApi.BASE_URL}/api/files/${it.collectionId}/${it.id}/${it.photo}",
                        it.subject
                    )
                }))

                is Resource.Error -> emit(
                    Resource.Error(
                        message = res.message ?: "Something went wrong!",
                        statusCode = res.statusCode
                    )
                )

                is Resource.Loading -> emit(Resource.Loading(res.isLoading))
            }
        }
    }

    override suspend fun generateSketch(
        url: String,
        subject: String
    ): Flow<Resource<List<String>>> = flow {
        safeApiCall {
            sketchApi.generateSketch(GenerateSketchRequestDto(url, subject))
        }.collect {
            when (it) {
                is Resource.Success -> emit(Resource.Success(it.data?.getOrNull(0) as List<String>))
                is Resource.Error -> emit(
                    Resource.Error(
                        message = it.message ?: "Something went wrong!",
                        statusCode = it.statusCode
                    )
                )

                is Resource.Loading -> emit(Resource.Loading(it.isLoading))
            }
        }
    }
}