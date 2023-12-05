package com.android.maxclub.easynotes.feature.notes.presentation.notes

sealed class NotesUiAction {
    data class ShowNoteDeletedMessage(val deletedNoteId: Int) : NotesUiAction()
}