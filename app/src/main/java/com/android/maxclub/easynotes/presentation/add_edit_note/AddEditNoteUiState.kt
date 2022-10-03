package com.android.maxclub.easynotes.presentation.add_edit_note

import com.android.maxclub.easynotes.domain.model.Note

sealed class AddEditNoteUiState(
    val isColorSectionEnabled: Boolean,
) {
    object Loading : AddEditNoteUiState(false)

    data class Success(
        val note: Note,
        val isColorSectionVisible: Boolean = false,
    ) : AddEditNoteUiState(true)
}
