package com.android.maxclub.easynotes.feature.notes.presentation.notes

import com.android.maxclub.easynotes.feature.notes.domain.models.Note
import com.android.maxclub.easynotes.feature.notes.domain.models.NoteOrder

data class NotesUiState(
    val isLoading: Boolean,
    val notes: List<Note>,
    val noteOrder: NoteOrder,
    val isOrderSectionVisible: Boolean,
)
