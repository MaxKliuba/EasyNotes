package com.android.maxclub.easynotes.presentation.notes

import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.util.NoteOrder

sealed class NotesEvent {
    object OnToggleOrderSection : NotesEvent()
    data class OnChangeOrder(val noteOrder: NoteOrder) : NotesEvent()
    object OnRefreshNotes : NotesEvent()
    object OnCreateNote : NotesEvent()
    data class OnClickNote(val note: Note) : NotesEvent()
    data class OnDeleteNote(val note: Note) : NotesEvent()
    data class OnRestoreNote(val deletedNote: Note) : NotesEvent()
}
