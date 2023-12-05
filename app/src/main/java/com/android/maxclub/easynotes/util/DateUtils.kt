package com.android.maxclub.easynotes.util

import android.content.Context
import com.android.maxclub.easynotes.R
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(
    date: Date,
    pattern: String = "MMMM dd yyyy, HH:mm",
    locale: Locale = Locale.getDefault(),
): String = SimpleDateFormat(pattern, locale)
    .format(date)
    .replaceFirstChar { it.uppercaseChar() }


fun formatDate(
    date: Date,
    context: Context,
): String = formatDate(
    date = date,
    pattern = context.getString(R.string.pattern),
    locale = Locale(context.getString(R.string.language), context.getString(R.string.country))
)