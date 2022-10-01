package com.android.maxclub.easynotes.data.repository

import com.android.maxclub.easynotes.data.local.NoteDao
import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.repository.NoteRepository
import com.android.maxclub.easynotes.domain.util.toNote
import com.android.maxclub.easynotes.domain.util.toNoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val coroutineDispatcher: CoroutineDispatcher,
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> =
        noteDao.getNotes()
            .map { entities ->
                entities.map { entity ->
                    entity.toNote()
                }
            }
            .flowOn(coroutineDispatcher)

    override fun getNoteById(id: Int): Flow<Note> =
        noteDao.getNoteById(id)
            .map { entity ->
                entity.toNote()
            }
            .flowOn(coroutineDispatcher)

    override suspend fun insertNotes(vararg notes: Note) = withContext(coroutineDispatcher) {
        noteDao.insertNotes(
            *notes.map { note ->
                note.toNoteEntity()
            }
                .toTypedArray()
        )
    }

    override suspend fun updateNotes(vararg notes: Note) = withContext(coroutineDispatcher) {
        noteDao.updateNotes(
            *notes.map { note ->
                note.toNoteEntity()
            }
                .toTypedArray()
        )
    }

    override suspend fun deleteNotes(vararg notes: Note) = withContext(coroutineDispatcher) {
        noteDao.deleteNotes(
            *notes.map { note ->
                note.toNoteEntity()
            }
                .toTypedArray()
        )
    }
}