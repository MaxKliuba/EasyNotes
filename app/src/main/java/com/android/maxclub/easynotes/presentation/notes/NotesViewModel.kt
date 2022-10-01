package com.android.maxclub.easynotes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.usecase.NoteUseCases
import com.android.maxclub.easynotes.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _uiState = mutableStateOf(NotesUiState(isLoading = true))
    val uiState: State<NotesUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<NotesUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var getNotesJob: Job? = null

    init {
        fetchNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OnToggleOrderSection -> {
                _uiState.value = uiState.value.copy(
                    isOrderSectionVisible = !uiState.value.isOrderSectionVisible
                )
            }
            is NotesEvent.OnChangeOrder -> {
                fetchNotes(noteOrder = event.noteOrder)
            }
            is NotesEvent.OnRefreshNotes -> {
                fetchNotes()
            }
            is NotesEvent.OnCreateNote -> {
                viewModelScope.launch {
                    // TODO
                    // _uiEvent.emit(NotesUiEvent.OnShowNote())
                    noteUseCases.addNote(
                        Note(
                            title = "Title",
                            content = "Content...",
                            color = Note.COLORS[0],
                        )
                    )
                }
            }
            is NotesEvent.OnClickNote -> {
                viewModelScope.launch {
                    _uiEvent.emit(NotesUiEvent.OnShowNote(event.note.id))
                }
            }
            is NotesEvent.OnDeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    _uiEvent.emit(NotesUiEvent.OnShowDeleteMessage(event.note))
                }
            }
            is NotesEvent.OnRestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(event.deletedNote)
                }
            }
        }
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
                exception.printStackTrace()
                _uiState.value = uiState.value.copy(
                    noteOrder = noteOrder,
                    isLoading = false,
                )
                viewModelScope.launch {
                    _uiEvent.emit(NotesUiEvent.OnShowErrorMessage)
                }
            }
            .launchIn(viewModelScope)
    }
}