package com.android.maxclub.easynotes.domain.model

import androidx.compose.ui.graphics.toArgb
import com.android.maxclub.easynotes.ui.theme.*
import java.util.Date

data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Date = Date(),
    val id: Int = 0,
) {
    companion object {
        val COLORS: List<Int> = listOf(Violet, BabyBlue, LightGreen, RedOrange, RedPink)
            .map { color -> color.toArgb() }
    }
}
