package com.android.maxclub.easynotes.domain.usecase

import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNote @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(noteId: Int): Flow<Note> = noteRepository.getNoteById(noteId)
}