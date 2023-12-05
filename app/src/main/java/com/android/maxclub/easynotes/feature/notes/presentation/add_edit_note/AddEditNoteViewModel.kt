package com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.maxclub.easynotes.feature.notes.domain.models.Note
import com.android.maxclub.easynotes.feature.notes.domain.repositories.NoteRepository
import com.android.maxclub.easynotes.util.Screen
import com.android.maxclub.easynotes.util.debounce
import com.android.maxclub.easynotes.util.noteColors
import com.android.maxclub.easynotes.util.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val initNoteId: Int = savedStateHandle[Screen.AddEditNoteScreen.ARG_NOTE_ID]
        ?: Screen.AddEditNoteScreen.DEFAULT_NOTE_ID

    private val _uiState = mutableStateOf<AddEditNoteUiState>(AddEditNoteUiState.Loading)
    val uiState: State<AddEditNoteUiState> = _uiState

    init {
        if (_uiState.value is AddEditNoteUiState.Loading) {
            getNoteById(initNoteId)
        }
    }

    private val updateNoteWithDebounce: (Note) -> Unit =
        viewModelScope.debounce(timeoutMillis = 500L) { note ->
            noteRepository.updateNotes(note)
        }

    fun toggleNoteColorSection() {
        (_uiState.value as? AddEditNoteUiState.Success)?.let { state ->
            _uiState.update { state.copy(isColorSectionVisible = !state.isColorSectionVisible) }
        }
    }

    fun changeNoteColor(color: Int) {
        (_uiState.value as? AddEditNoteUiState.Success)?.let { state ->
            viewModelScope.launch {
                noteRepository.updateNotes(
                    state.note.copy(color = color)
                )
            }
        }
    }

    fun tryChangeNoteTitle(value: TextFieldValue) {
        if (value.text.length <= MAX_TITLE_LENGTH) {
            (_uiState.value as? AddEditNoteUiState.Success)?.let { state ->
                _uiState.update { state.copy(noteTitle = value) }
                updateNoteWithDebounce(state.note.copy(title = value.text))
            }
        }
    }

    fun tryChangeNoteContent(value: TextFieldValue) {
        if (value.text.length <= MAX_CONTENT_LENGTH) {
            (_uiState.value as? AddEditNoteUiState.Success)?.let { state ->
                _uiState.update { state.copy(noteContent = value) }
                updateNoteWithDebounce(state.note.copy(content = value.text))
            }
        }
    }

    private fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            val newNoteId =
                if (noteId == Screen.AddEditNoteScreen.DEFAULT_NOTE_ID) {
                    noteRepository.addNote(
                        Note(
                            title = "",
                            content = "",
                            color = noteColors.first(),
                        )
                    )
                } else {
                    noteId
                }

            noteRepository.getNoteById(newNoteId)
                .onStart {
                    _uiState.update { AddEditNoteUiState.Loading }
                }
                .onEach { note ->
                    val noteTitle = note.title
                    val noteContent = note.content

                    _uiState.update {
                        if (it is AddEditNoteUiState.Success) {
                            it.copy(
                                noteTitle = if (it.noteTitle.text == noteTitle) {
                                    it.noteTitle
                                } else {
                                    TextFieldValue(noteTitle, TextRange(noteTitle.length))
                                },
                                noteContent = if (it.noteContent.text == noteContent) {
                                    it.noteContent
                                } else {
                                    TextFieldValue(noteContent, TextRange(noteContent.length))
                                },
                                note = note,
                            )
                        } else {
                            AddEditNoteUiState.Success(
                                noteTitle = TextFieldValue(noteTitle, TextRange(noteTitle.length)),
                                noteContent = TextFieldValue(
                                    noteContent,
                                    TextRange(noteContent.length)
                                ),
                                note = note,
                                isColorSectionVisible = noteId == Screen.AddEditNoteScreen.DEFAULT_NOTE_ID,
                            )
                        }
                    }
                }
                .catch { it.printStackTrace() }
                .launchIn(viewModelScope)
        }
    }

    companion object {
        private const val MAX_TITLE_LENGTH = 100
        private const val MAX_CONTENT_LENGTH = 1000
    }
}