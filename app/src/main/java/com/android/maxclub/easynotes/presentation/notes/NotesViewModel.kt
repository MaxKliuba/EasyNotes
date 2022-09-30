package com.android.maxclub.easynotes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.maxclub.easynotes.domain.usecase.NoteUseCases
import com.android.maxclub.easynotes.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _uiState = mutableStateOf(NotesUiState(isLoading = true))
    val uiState: State<NotesUiState> = _uiState

    private var getNotesJob: Job? = null

    init {
        fetchNotes()
    }

    private fun fetchNotes(noteOrder: NoteOrder = uiState.value.noteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onStart {
                _uiState.value = uiState.value.copy(
                    noteOrder = noteOrder,
                    isLoading = true,
                )
            }
            .onEach { notes ->
                _uiState.value = uiState.value.copy(
                    notes = notes,
                    noteOrder = noteOrder,
                    isLoading = false,
                )
            }
            .catch { exception ->
                _uiState.value = uiState.value.copy(
                    noteOrder = noteOrder,
                    isLoading = false,
                )
            }
            .launchIn(viewModelScope)
    }
}