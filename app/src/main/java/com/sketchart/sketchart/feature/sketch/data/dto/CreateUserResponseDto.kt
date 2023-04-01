package com.sketchart.sketchart.feature.sketch.data.dto

import com.sketchart.sketchart.feature.sketch.domain.models.User

data class CreateUserResponseDto(
    val avatar: String,
    val collectionId: String,
    val collectionName: String,
    val created: String,
    val emailVisibility: Boolean,
    val id: String,
    val name: String,
    val updated: String,
    val username: String,
    val email: String?,
    val verified: Boolean
)

fun CreateUserResponseDto.toUser() = User(avatar, id, name, username, verified)