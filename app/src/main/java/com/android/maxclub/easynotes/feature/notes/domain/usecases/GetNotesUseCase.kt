package com.android.maxclub.easynotes.feature.notes.domain.usecases

import com.android.maxclub.easynotes.feature.notes.domain.models.Note
import com.android.maxclub.easynotes.feature.notes.domain.models.NoteOrder
import com.android.maxclub.easynotes.feature.notes.domain.repositories.NoteRepository
import com.android.maxclub.easynotes.util.noteColors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
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
                is NoteOrder.ByColor -> notes.sortedBy(isDescending) { noteColors.indexOf(it.color) }
            }
        }

    private fun <T, R : Comparable<R>> List<T>.sortedBy(
        isDescending: Boolean = true,
        selector: (T) -> R?
    ): List<T> = if (isDescending) sortedByDescending(selector) else sortedBy(selector)
}