package com.android.maxclub.easynotes.feature.notes.domain.models

import java.util.Date

data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Date = Date(),
    val id: Int = 0,
)