package com.sketchart.sketchart.feature.sketch.presentation.screens.sketchgenerate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceRepository
import com.sketchart.sketchart.core.util.Resource
import com.sketchart.sketchart.feature.sketch.domain.repository.SketchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SketchGenerateViewModel @Inject constructor(
    private val repository: SketchRepository,
    private val prefRepository: AppPreferenceRepository,
) :
    ViewModel() {

    var state by mutableStateOf(SketchGenerateState())

    init {
        getSketches()
    }

    private fun getSketches() {
        state = state.copy(error = "")
        viewModelScope.launch {
            repository.getSketches("artist_id=\"5h6ih9nbobo64g9\"")
                .collect { result ->
                    state = when (result) {
                        is Resource.Success -> {
                            val item = result.data?.getOrNull(0)
                            generateSketches(item?.photo, item?.subject)
                            state.copy(
                                sketchList = result.data,
                                error = ""
                            )
                        }

                        is Resource.Error -> {
                            state.copy(
                                error = result.message.toString(),
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

    private fun generateSketches(url: String?, subject: String?) {

        if (url.isNullOrEmpty() || subject.isNullOrEmpty()) return

        state = state.copy(error = "")
        viewModelScope.launch {
            repository.generateSketch(url, subject)
                .collect { result ->
                    state = when (result) {
                        is Resource.Success -> {
                            state.copy(
                                generatedSketchList = result.data,
                                error = ""
                            )
                        }

                        is Resource.Error -> {
                            state.copy(
                                error = result.message.toString(),
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
}