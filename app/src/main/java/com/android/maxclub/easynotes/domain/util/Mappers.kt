package com.android.maxclub.easynotes.domain.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.android.maxclub.easynotes.data.local.entity.NoteEntity
import com.android.maxclub.easynotes.domain.model.Note
import java.util.Date

fun Note.toNoteEntity(
    id: Int = this.id,
    timestamp: Long = this.timestamp.time,
    title: String = this.title,
    content: String = this.content,
    color: Int = this.color.toArgb(),
): NoteEntity = NoteEntity(
    id = id,
    timestamp = timestamp,
    title = title,
    content = content,
    color = color,
)

fun NoteEntity.toNote(
    title: String = this.title,
    content: String = this.content,
    color: Int = this.color,
    timestamp: Date = Date(this.timestamp),
    id: Int = this.id,
): Note = Note(
    title = title,
    content = content,
    color = Color(color),
    timestamp = timestamp,
    id = id,
)