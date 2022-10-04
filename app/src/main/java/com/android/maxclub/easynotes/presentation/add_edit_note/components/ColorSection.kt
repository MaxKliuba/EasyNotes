package com.android.maxclub.easynotes.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.android.maxclub.easynotes.domain.model.Note

@Composable
fun ColorSection(
    noteColor: Int,
    onChangeColor: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = Note.COLORS,
            key = { it },
        ) { color ->
            ColorPickerItem(
                color = Color(color),
                selected = noteColor == color,
                onSelect = { onChangeColor(it.toArgb()) },
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}