package com.android.maxclub.easynotes.feature.notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.maxclub.easynotes.feature.notes.domain.models.NoteOrder
import com.android.maxclub.easynotes.feature.notes.domain.repositories.NoteRepository
import com.android.maxclub.easynotes.feature.notes.domain.usecases.GetNotesUseCase
import com.android.maxclub.easynotes.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val getNotesUseCase: GetNotesUseCase,
) : ViewModel() {

    private val _uiState = mutableStateOf(
        NotesUiState(
            isLoading = true,
            notes = emptyList(),
            noteOrder = NoteOrder.ByTimestamp(isDescending = true),
            isOrderSectionVisible = false,
        )
    )
    val uiState: State<NotesUiState> = _uiState

    private val _uiActionChannel = Channel<NotesUiAction>()
    val uiAction = _uiActionChannel.receiveAsFlow()

    private var getNotesJob: Job? = null

    init {
        if (_uiState.value.isLoading) {
            getNotes(_uiState.value.noteOrder)
        }

        viewModelScope.launch {
            noteRepository.deleteMarkedAsDeletedNotes()
        }
    }

    fun toggleOrderSection() {
        _uiState.update { it.copy(isOrderSectionVisible = !_uiState.value.isOrderSectionVisible) }
    }

    fun changeNoteOrder(noteOrder: NoteOrder) {
        getNotes(noteOrder)

    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            noteRepository.deleteNoteById(noteId)
            _uiActionChannel.send(NotesUiAction.ShowNoteDeletedMessage(noteId))
        }
    }

    fun tryRestoreNote(noteId: Int) {
        viewModelScope.launch {
            noteRepository.tryRestoreNoteById(noteId)
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = getNotesUseCase(noteOrder)
            .onStart {
                _uiState.update { state ->
                    state.copy(
                        isLoading = true,
                        noteOrder = noteOrder,
                    )
                }
            }
            .onEach { notes ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        notes = notes,
                        noteOrder = noteOrder,
                    )
                }
            }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }
}