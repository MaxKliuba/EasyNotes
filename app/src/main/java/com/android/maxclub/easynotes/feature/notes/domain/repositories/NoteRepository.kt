package com.android.maxclub.easynotes.feature.notes.domain.repositories

import com.android.maxclub.easynotes.feature.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    fun getNoteById(id: Int): Flow<Note>

    suspend fun addNote(note: Note): Int

    suspend fun updateNotes(vararg notes: Note)

    suspend fun deleteNoteById(noteId: Int)

    suspend fun tryRestoreNoteById(noteId: Int)

    suspend fun deleteMarkedAsDeletedNotes()
}