package com.android.maxclub.easynotes.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteUseCases: NoteUseCases,
) : ViewModel() {
    private val noteId: Int = savedStateHandle["noteId"] ?: -1
    private var initNote: Note? = null

    private val _uiState = mutableStateOf(
        if (noteId == -1) {
            AddEditNoteUiState.Success(
                note = Note(
                    title = "",
                    content = "",
                    color = Note.COLORS.first(),
                ).also { initNote = it },
                isColorSectionVisible = true,
            )
        } else {
            AddEditNoteUiState.Loading
        }
    )
    val uiState: State<AddEditNoteUiState> = _uiState

    private var getNoteJob: Job? = null

    init {
        if (uiState.value is AddEditNoteUiState.Loading) {
            getNoteById(noteId)
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.OnChangeTitle -> {
                uiState.value.let { state ->
                    if (state is AddEditNoteUiState.Success && state.note.title.length <= MAX_TITLE_LENGTH) {
                        _uiState.value = state.copy(
                            note = state.note.copy(title = event.value)
                        )
                    }
                }
            }
            is AddEditNoteEvent.OnChangeContent -> {
                uiState.value.let { state ->
                    if (state is AddEditNoteUiState.Success && state.note.content.length <= MAX_CONTENT_LENGTH) {
                        _uiState.value = state.copy(
                            note = state.note.copy(content = event.value)
                        )
                    }
                }
            }
            is AddEditNoteEvent.OnChangeColor -> {
                uiState.value.let { state ->
                    if (state is AddEditNoteUiState.Success) {
                        _uiState.value = state.copy(
                            note = state.note.copy(color = event.color)
                        )
                    }
                }
            }
            is AddEditNoteEvent.OnColorSectionToggle -> {
                uiState.value.let { state ->
                    if (state is AddEditNoteUiState.Success) {
                        _uiState.value = state.copy(
                            isColorSectionVisible = !state.isColorSectionVisible
                        )
                    }
                }
            }
            is AddEditNoteEvent.OnSaveNote -> {
                uiState.value.let { state ->
                    if (state is AddEditNoteUiState.Success) {
                        if (initNote != state.note) {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (state.note.title.isEmpty() && state.note.content.isEmpty()) {
                                    noteUseCases.deleteNote(state.note)
                                } else {
                                    noteUseCases.addNote(state.note.copy(timestamp = Date()))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getNoteById(noteId: Int) {
        getNoteJob?.cancel()
        getNoteJob = noteUseCases.getNote(noteId)
            .onStart {
                _uiState.value = AddEditNoteUiState.Loading
            }
            .onEach { note ->
                initNote = note
                _uiState.value = AddEditNoteUiState.Success(
                    note = note,
                    isColorSectionVisible = uiState.value.let { state ->
                        if (state is AddEditNoteUiState.Success) {
                            state.isColorSectionVisible
                        } else {
                            false
                        }
                    }
                )
            }
            .catch { exception ->
                exception.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        onEvent(AddEditNoteEvent.OnSaveNote)
    }

    companion object {
        private const val MAX_TITLE_LENGTH = 100
        private const val MAX_CONTENT_LENGTH = 1000
    }
}