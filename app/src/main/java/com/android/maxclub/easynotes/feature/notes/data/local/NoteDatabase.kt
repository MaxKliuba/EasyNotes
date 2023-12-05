package com.android.maxclub.easynotes.feature.notes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.maxclub.easynotes.feature.notes.data.local.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        private const val DATABASE_NAME = "notes_db"

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
    }
}