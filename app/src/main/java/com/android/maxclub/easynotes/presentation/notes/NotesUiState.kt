package com.android.maxclub.easynotes.presentation.notes

import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.util.NoteOrder
import com.android.maxclub.easynotes.domain.util.OrderDirection

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.ByTimestamp(OrderDirection.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isLoading: Boolean = false,
)
