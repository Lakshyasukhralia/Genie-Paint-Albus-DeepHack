package com.sketchart.sketchart.feature.sketch.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceKeys
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceKeys.AUTHORIZATION_TOKEN
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceKeys.USER_ID
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceRepository
import com.sketchart.sketchart.core.util.Resource
import com.sketchart.sketchart.feature.sketch.domain.repository.SketchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UserAuthViewModel @Inject constructor(
    private val repository: SketchRepository,
    private val prefRepository: AppPreferenceRepository,
) :
    ViewModel() {

    var state by mutableStateOf(UserAuthState())

    private fun validateUserNameAndPassword(email: String?, password: String): Boolean {
        if (email.isNullOrEmpty() || password.isEmpty()) return false
        return true
    }

    fun createUser(email: String?, username: String?, password: String, passwordConfirm: String) {

        if (validateUserNameAndPassword(email, password).not()) {
            state = state.copy(error = "Invalid input")
            return
        }

        state = state.copy(error = "")

        viewModelScope.launch {
            repository.createUser(email, username, password, passwordConfirm)
                .collect { result ->
                    state = when (result) {
                        is Resource.Success -> {
                            state.copy(
                                isSignedUp = true,
                                error = ""
                            )
                        }
                        is Resource.Error -> {
                            state.copy(
                                error = result.message.toString(),
                                isSignedUp = false
                            )
                        }
                        is Resource.Loading -> {
                            state.copy(
                                isLoading = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    fun authenticateUser(identity: String, password: String) {

        if (validateUserNameAndPassword(identity, password).not()) {
            state = state.copy(error = "Invalid input")
            return
        }
        state = state.copy(error = "")

        viewModelScope.launch {
            repository.authenticateUser(identity, password)
                .collect { result ->
                    state = when (result) {
                        is Resource.Success -> {
                            if (result.data?.token.isNullOrEmpty().not()) {
                                saveAuthToken(result.data?.token!!)
                                result.data.id?.let { saveUserId(it) }
                            }
                            state.copy(
                                isSignedIn = true,
                                error = ""
                            )
                        }
                        is Resource.Error -> {
                            state.copy(
                                error = result.message.toString(),
                                isSignedIn = false
                            )
                        }
                        is Resource.Loading -> {
                            state.copy(
                                isLoading = result.isLoading,
                            )
                        }
                    }
                }
        }
    }

    private suspend fun saveAuthToken(token: String) {
        prefRepository.putString(AUTHORIZATION_TOKEN, token)
    }

    fun getAuthToken(): String? = runBlocking {
        prefRepository.getString(AUTHORIZATION_TOKEN)
    }

    private suspend fun saveUserId(userId: String) {
        prefRepository.putString(USER_ID, userId)
    }

    fun saveUserRole(token: String) = runBlocking {
        prefRepository.putString(AppPreferenceKeys.USER_ROLE, token)
    }

    fun getUserRole(): String? = runBlocking {
        prefRepository.getString(AppPreferenceKeys.USER_ROLE)
    }
}