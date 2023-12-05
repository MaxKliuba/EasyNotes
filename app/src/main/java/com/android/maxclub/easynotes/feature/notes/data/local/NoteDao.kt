package com.android.maxclub.easynotes.feature.notes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.maxclub.easynotes.feature.notes.data.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE isDeleted = 0")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id AND isDeleted = 0")
    fun getNoteById(id: Int): Flow<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNotes(vararg notes: NoteEntity)

    @Query("UPDATE notes SET isDeleted = 1 WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Query("UPDATE notes SET isDeleted = 0 WHERE id = :noteId")
    suspend fun tryRestoreNoteById(noteId: Int)

    @Query("DELETE FROM notes WHERE isDeleted = 1")
    suspend fun deleteMarkedAsDeletedNotes()
}