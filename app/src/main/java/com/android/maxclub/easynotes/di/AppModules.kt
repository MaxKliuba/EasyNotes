package com.android.maxclub.easynotes.di

import android.content.Context
import com.android.maxclub.easynotes.feature.notes.data.local.NoteDao
import com.android.maxclub.easynotes.feature.notes.data.local.NoteDatabase
import com.android.maxclub.easynotes.feature.notes.data.repositories.NoteRepositoryImpl
import com.android.maxclub.easynotes.feature.notes.domain.repositories.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        noteRepository: NoteRepositoryImpl
    ): NoteRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NoteDatabaseModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        NoteDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao =
        noteDatabase.noteDao
}