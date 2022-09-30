package com.android.maxclub.easynotes.domain.repository

import com.android.maxclub.easynotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    fun getNoteById(id: Int): Flow<Note>

    suspend fun insertNotes(vararg notes: Note)

    suspend fun updateNotes(vararg notes: Note)

    suspend fun deleteNotes(vararg notes: Note)
}