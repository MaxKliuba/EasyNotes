package com.android.maxclub.easynotes.domain.usecase

import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.repository.NoteRepository
import com.android.maxclub.easynotes.domain.util.NoteOrder
import com.android.maxclub.easynotes.domain.util.OrderDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotes @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.ByTimestamp(OrderDirection.Descending)
    ): Flow<List<Note>> = noteRepository.getNotes()
        .map { notes ->
            val isAsc = noteOrder.direction == OrderDirection.Ascending
            when (noteOrder) {
                is NoteOrder.ByTimestamp -> notes.sortedBy(isAsc) { it.timestamp }
                is NoteOrder.ByTitle -> notes.sortedBy(isAsc) { it.title.lowercase() }
                is NoteOrder.ByColor -> notes.sortedBy(isAsc) { Note.COLORS.indexOf(it.color) }
            }
        }
}

private fun <T, R : Comparable<R>> List<T>.sortedBy(
    isAsc: Boolean = true,
    selector: (T) -> R?
): List<T> = if (isAsc) sortedBy(selector) else sortedByDescending(selector)