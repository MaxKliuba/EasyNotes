package com.android.maxclub.easynotes.presentation.notes

import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.util.NoteOrder

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.ByTimestamp(isDescending = true),
    val isOrderSectionVisible: Boolean = false,
    val isLoading: Boolean = false,
)
