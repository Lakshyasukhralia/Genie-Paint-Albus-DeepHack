package com.sketchart.sketchart.feature.sketch.presentation.screens.sketch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceRepository
import com.sketchart.sketchart.core.util.Resource
import com.sketchart.sketchart.feature.sketch.domain.models.Sketch
import com.sketchart.sketchart.feature.sketch.domain.repository.SketchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SketchViewModel @Inject constructor(
    private val repository: SketchRepository,
    private val prefRepository: AppPreferenceRepository,
) :
    ViewModel() {

    var state by mutableStateOf(SketchState())

    fun createSketch(photo: String, subject: String) {

        if (subject.isEmpty()) {
            state = state.copy(
                error = "Please add subject",
            )
            return
        }

        viewModelScope.launch {
            state = state.copy(
                error = "",
            )

            repository.createSketch(Sketch("5h6ih9nbobo64g9", photo, subject))
                .collect { result ->
                    state = when (result) {
                        is Resource.Success -> {
                            state.copy(
                                error = "",
                                isSuccess = true
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