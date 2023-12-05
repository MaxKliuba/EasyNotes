package com.android.maxclub.easynotes.feature.notes.data.repositories

import com.android.maxclub.easynotes.feature.notes.data.local.NoteDao
import com.android.maxclub.easynotes.feature.notes.data.mappers.toNote
import com.android.maxclub.easynotes.feature.notes.data.mappers.toNoteEntity
import com.android.maxclub.easynotes.feature.notes.domain.models.Note
import com.android.maxclub.easynotes.feature.notes.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> =
        noteDao.getNotes()
            .map { entities ->
                entities.map { entity ->
                    entity.toNote()
                }
            }

    override fun getNoteById(id: Int): Flow<Note> =
        noteDao.getNoteById(id)
            .map { entity ->
                entity.toNote()
            }

    override suspend fun addNote(note: Note): Int {
        val noteEntity = note.toNoteEntity()
        return noteDao.insertNote(noteEntity).toInt()
    }

    override suspend fun updateNotes(vararg notes: Note) {
        val noteEntities = notes.map { it.toNoteEntity() }
        noteDao.updateNotes(*noteEntities.toTypedArray())
    }

    override suspend fun deleteNoteById(noteId: Int) {
        noteDao.deleteNoteById(noteId)
    }

    override suspend fun tryRestoreNoteById(noteId: Int) {
        noteDao.tryRestoreNoteById(noteId)
    }

    override suspend fun deleteMarkedAsDeletedNotes() {
        noteDao.deleteMarkedAsDeletedNotes()
    }
}