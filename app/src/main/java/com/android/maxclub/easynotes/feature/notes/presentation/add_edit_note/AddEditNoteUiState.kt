package com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note

import androidx.compose.ui.text.input.TextFieldValue
import com.android.maxclub.easynotes.feature.notes.domain.models.Note

sealed class AddEditNoteUiState {
    object Loading : AddEditNoteUiState()

    data class Success(
        val noteTitle: TextFieldValue,
        val noteContent: TextFieldValue,
        val note: Note,
        val isColorSectionVisible: Boolean = false,
    ) : AddEditNoteUiState()
}
