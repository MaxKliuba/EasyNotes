package com.android.maxclub.easynotes.feature.notes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    val timestamp: Long,
    val title: String,
    val content: String,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isDeleted: Boolean = false,
)
