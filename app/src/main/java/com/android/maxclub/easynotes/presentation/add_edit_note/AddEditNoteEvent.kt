package com.android.maxclub.easynotes.presentation.add_edit_note

sealed class AddEditNoteEvent {
    data class OnChangeTitle(val value: String) : AddEditNoteEvent()
    data class OnChangeContent(val value: String) : AddEditNoteEvent()
    data class OnChangeColor(val color: Int) : AddEditNoteEvent()
    object OnColorSectionToggle : AddEditNoteEvent()
    object OnSaveNote : AddEditNoteEvent()
}
