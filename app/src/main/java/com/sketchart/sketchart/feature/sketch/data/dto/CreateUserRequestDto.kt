package com.sketchart.sketchart.feature.sketch.data.dto
import com.google.gson.annotations.SerializedName

data class CreateUserRequestDto(
    @SerializedName("email")
    val email: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String,
    @SerializedName("passwordConfirm")
    val passwordConfirm: String,
    @SerializedName("name")
    val name: String?
)
