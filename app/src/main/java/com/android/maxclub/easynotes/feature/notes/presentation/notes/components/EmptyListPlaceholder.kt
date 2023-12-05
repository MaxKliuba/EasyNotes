package com.android.maxclub.easynotes.feature.notes.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.maxclub.easynotes.R

@Composable
fun EmptyListPlaceholder(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.wrapContentSize()) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_notes_24),
            contentDescription = stringResource(R.string.empty_list_text),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
        )

        Text(
            text = stringResource(R.string.empty_list_text),
            textAlign = TextAlign.Center,
        )
    }
}