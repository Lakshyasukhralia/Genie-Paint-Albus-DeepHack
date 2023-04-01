package com.sketchart.sketchart.feature.sketch.data.dto

import com.sketchart.sketchart.feature.sketch.domain.models.User

data class AuthenticateResponseDto(
    val record: UserRecordDto,
    val token: String
)

data class UserRecordDto(
    val avatar: String,
    val collectionId: String,
    val collectionName: String,
    val created: String,
    val email: String,
    val emailVisibility: Boolean,
    val id: String,
    val name: String,
    val updated: String,
    val username: String,
    val verified: Boolean
)

fun UserRecordDto.toUser() = User(avatar, id, name, username, verified)

fun AuthenticateResponseDto.toUser() =
    User(record.avatar, record.id, record.name, record.username, record.verified, token)