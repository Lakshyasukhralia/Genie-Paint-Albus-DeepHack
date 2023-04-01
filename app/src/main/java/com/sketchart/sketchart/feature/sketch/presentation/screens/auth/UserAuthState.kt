package com.sketchart.sketchart.feature.sketch.presentation.screens.auth

data class UserAuthState(
    val isLoading: Boolean = false,
    var error: String = "",
    val isSignedUp: Boolean = false,
    val isSignedIn: Boolean = false,
)