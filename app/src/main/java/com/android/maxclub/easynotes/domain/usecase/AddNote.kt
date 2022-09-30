package com.android.maxclub.easynotes.domain.usecase

import com.android.maxclub.easynotes.domain.model.Note
import com.android.maxclub.easynotes.domain.repository.NoteRepository
import javax.inject.Inject

class AddNote @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = noteRepository.insertNotes(note)
}