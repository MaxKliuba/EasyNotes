package com.android.maxclub.easynotes.util

import androidx.compose.ui.graphics.toArgb
import com.android.maxclub.easynotes.ui.theme.BabyBlue
import com.android.maxclub.easynotes.ui.theme.LightGreen
import com.android.maxclub.easynotes.ui.theme.RedOrange
import com.android.maxclub.easynotes.ui.theme.RedPink
import com.android.maxclub.easynotes.ui.theme.Violet

val noteColors: List<Int> = listOf(Violet, BabyBlue, LightGreen, RedOrange, RedPink)
    .map { color -> color.toArgb() }