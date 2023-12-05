package com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.android.maxclub.easynotes.R

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteTopAppBar(
    onNavigateUp: () -> Unit,
    onToggleNoteColorSection: () -> Unit,
    isNoteColorSectionEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = stringResource(R.string.navigate_up_text),
                )
            }
        },
        actions = {
            IconButton(
                onClick = onToggleNoteColorSection,
                enabled = isNoteColorSectionEnabled,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_color_24),
                    contentDescription = stringResource(R.string.select_color_text),
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
    )
}