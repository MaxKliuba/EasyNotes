package com.android.maxclub.easynotes.presentation.notes

import com.android.maxclub.easynotes.domain.model.Note

sealed class NotesUiEvent {
    object OnCreateNote : NotesUiEvent()
    data class OnShowNote(val note: Note) : NotesUiEvent()
    data class OnShowDeleteMessage(val deletedNote: Note) : NotesUiEvent()
    object OnShowErrorMessage : NotesUiEvent()
}