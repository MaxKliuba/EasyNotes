package com.android.maxclub.easynotes.feature.notes.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.maxclub.easynotes.R
import com.android.maxclub.easynotes.feature.notes.presentation.notes.components.EmptyListPlaceholder
import com.android.maxclub.easynotes.feature.notes.presentation.notes.components.NoteList
import com.android.maxclub.easynotes.feature.notes.presentation.notes.components.NotesTopAppBar
import com.android.maxclub.easynotes.feature.notes.presentation.notes.components.OrderSection
import kotlinx.coroutines.flow.collectLatest

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    onAddNote: () -> Unit,
    onEditNote: (Int) -> Unit,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState

    val context = LocalContext.current
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiAction.collectLatest { action ->
            if (action is NotesUiAction.ShowNoteDeletedMessage) {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.note_deleted_message_text),
                    actionLabel = context.getString(R.string.undo_text),
                    duration = SnackbarDuration.Short,
                ).let { result ->
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.tryRestoreNote(action.deletedNoteId)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            NotesTopAppBar(onToggleOrderSection = viewModel::toggleOrderSection)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionColor = MaterialTheme.colorScheme.primary,
                    snackbarData = data,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNote,
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = stringResource(R.string.add_note_text)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(
                visible = !state.isLoading && state.notes.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center),
            ) {
                EmptyListPlaceholder()
            }

            Column(modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(visible = state.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.background,
                    )
                }

                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                ) {
                    OrderSection(
                        noteOrder = state.noteOrder,
                        onChangeOrder = viewModel::changeNoteOrder,
                    )
                }

                AnimatedVisibility(visible = state.notes.isNotEmpty()) {
                    NoteList(
                        notes = state.notes,
                        onEditNote = onEditNote,
                        onDelete = viewModel::deleteNote,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}