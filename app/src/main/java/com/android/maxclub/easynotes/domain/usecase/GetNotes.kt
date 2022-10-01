package com.android.maxclub.easynotes.domain.usecase

import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.repository.NoteRepository
import com.android.maxclub.easynotes.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotes @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.ByTimestamp(isDescending = true)
    ): Flow<List<Note>> = noteRepository.getNotes()
        .map { notes ->
            val isDescending = noteOrder.isDescending
            when (noteOrder) {
                is NoteOrder.ByTimestamp -> notes.sortedBy(isDescending) { it.timestamp }
                is NoteOrder.ByTitle -> notes.sortedBy(isDescending) { it.title.lowercase() }
                is NoteOrder.ByColor -> notes.sortedBy(isDescending) { Note.COLORS.indexOf(it.color) }
            }
        }
}

private fun <T, R : Comparable<R>> List<T>.sortedBy(
    isDescending: Boolean = true,
    selector: (T) -> R?
): List<T> = if (isDescending) sortedByDescending(selector) else sortedBy(selector)