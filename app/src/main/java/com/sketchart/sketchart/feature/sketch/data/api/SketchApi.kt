package com.sketchart.sketchart.feature.sketch.data.api

import com.sketchart.sketchart.feature.sketch.data.dto.AuthenticateRequestDto
import com.sketchart.sketchart.feature.sketch.data.dto.AuthenticateResponseDto
import com.sketchart.sketchart.feature.sketch.data.dto.CreateUserRequestDto
import com.sketchart.sketchart.feature.sketch.data.dto.CreateUserResponseDto
import com.sketchart.sketchart.feature.sketch.data.dto.GenerateSketchRequestDto
import com.sketchart.sketchart.feature.sketch.data.dto.GetSketchResponseDto
import com.sketchart.sketchart.feature.sketch.data.dto.SketchItem
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SketchApi {

    @POST("$BASE_URL/api/collections/users/records")
    suspend fun createUser(@Body createUserRequest: CreateUserRequestDto): Response<CreateUserResponseDto>

    @POST("$BASE_URL/api/collections/users/auth-with-password")
    suspend fun authenticate(@Body authenticateRequest: AuthenticateRequestDto): Response<AuthenticateResponseDto>

    @POST("$BASE_URL/api/collections/sketches/records")
    suspend fun createSketch(@Body requestBody: RequestBody): Response<Void>

    @GET("$BASE_URL/api/collections/sketches/records")
    suspend fun getSketch(
        @Query("filter") filter: String? = "",
        @Query("expand") expand: String?,
        @Query("sort") sort: String? = "-created",
    ): Response<GetSketchResponseDto>

    @POST("$SKETCH_GENERATOR_URL/stable_diffusion_image_generator/")
    suspend fun generateSketch(@Body generateSketchRequestDto: GenerateSketchRequestDto): Response<List<Any>>

    companion object {

        const val BASE_URL = "http://172.105.39.153:85"
        const val SKETCH_GENERATOR_URL = "http://3.109.47.41:8001"

    }
}