package com.android.maxclub.easynotes.domain.model

import androidx.compose.ui.graphics.Color
import com.android.maxclub.easynotes.ui.theme.*
import java.util.Date

data class Note(
    val title: String,
    val content: String,
    val color: Color,
    val timestamp: Date = Date(),
    val id: Int = 0,
) {
    companion object {
        val COLORS: List<Color> = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
