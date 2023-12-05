package com.android.maxclub.easynotes.feature.notes.data.mappers

import com.android.maxclub.easynotes.feature.notes.data.local.entities.NoteEntity
import com.android.maxclub.easynotes.feature.notes.domain.models.Note
import java.util.Date

fun Note.toNoteEntity(
    timestamp: Long = this.timestamp.time,
    title: String = this.title,
    content: String = this.content,
    color: Int = this.color,
    id: Int = this.id,
): NoteEntity = NoteEntity(
    timestamp = timestamp,
    title = title,
    content = content,
    color = color,
    id = id,
    isDeleted = false,
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
    color = color,
    timestamp = timestamp,
    id = id,
)