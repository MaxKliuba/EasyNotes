package com.android.maxclub.easynotes.feature.notes.presentation.notes.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.maxclub.easynotes.feature.notes.domain.models.Note

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    notes: List<Note>,
    onEditNote: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = notes,
            key = { note -> note.id }
        ) { note ->
            NoteItem(
                note = note,
                onEdit = { onEditNote(note.id) },
                onDelete = { onDelete(note.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}