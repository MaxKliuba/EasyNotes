package com.android.maxclub.easynotes.di

import android.content.Context
import com.android.maxclub.easynotes.data.local.NoteDao
import com.android.maxclub.easynotes.data.local.NoteDatabase
import com.android.maxclub.easynotes.data.repository.NoteRepositoryImpl
import com.android.maxclub.easynotes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        NoteDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao =
        noteDatabase.noteDao()
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository =
        NoteRepositoryImpl(noteDao)
}