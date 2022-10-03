package com.android.maxclub.easynotes.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.maxclub.easynotes.R
import com.android.maxclub.easynotes.domain.util.NoteOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.ByTimestamp(),
    onChangeOrder: (NoteOrder) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_date_text),
                selected = noteOrder is NoteOrder.ByTimestamp,
                onClick = { onChangeOrder(NoteOrder.ByTimestamp(noteOrder.isDescending)) },
            )
            Spacer(modifier = Modifier.width(16.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_color_text),
                selected = noteOrder is NoteOrder.ByColor,
                onClick = { onChangeOrder(NoteOrder.ByColor(noteOrder.isDescending)) },
            )
            Spacer(modifier = Modifier.width(16.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_title_text),
                selected = noteOrder is NoteOrder.ByTitle,
                onClick = { onChangeOrder(NoteOrder.ByTitle(noteOrder.isDescending)) },
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.sort_ascending_text),
                selected = !noteOrder.isDescending,
                onClick = { onChangeOrder(noteOrder.copy(isDescending = false)) },
            )
            Spacer(modifier = Modifier.width(16.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_descending_text),
                selected = noteOrder.isDescending,
                onClick = { onChangeOrder(noteOrder.copy(isDescending = true)) },
            )
        }
    }
}