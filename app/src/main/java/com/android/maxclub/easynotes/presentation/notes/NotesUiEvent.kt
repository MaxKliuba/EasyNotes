package com.android.maxclub.easynotes.presentation.notes

import com.android.maxclub.easynotes.domain.model.Note

sealed class NotesUiEvent {
    data class OnShowNote(val noteId: Int = -1) : NotesUiEvent()
    data class OnShowDeleteMessage(val deletedNote: Note) : NotesUiEvent()
    object OnShowErrorMessage : NotesUiEvent()
}