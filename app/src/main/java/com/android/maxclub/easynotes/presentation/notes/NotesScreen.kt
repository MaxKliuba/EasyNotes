package com.android.maxclub.easynotes.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.maxclub.easynotes.R
import com.android.maxclub.easynotes.presentation.notes.components.NoteItem
import com.android.maxclub.easynotes.presentation.notes.components.OrderSection
import com.android.maxclub.easynotes.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val state: NotesUiState by viewModel.uiState
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is NotesUiEvent.OnShowDeleteMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.note_deleted_message_text),
                        actionLabel = context.getString(R.string.undo_text),
                        duration = SnackbarDuration.Short,
                    ).let { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(NotesEvent.OnRestoreNote(event.deletedNote))
                        }
                    }
                }
                is NotesUiEvent.OnShowNote -> {
                    // TODO
//                    navController.navigate(
//                        "${Screen.AddEditNoteScreen.route}?noteId=${event.noteId}"
//                    )
                }
                is NotesUiEvent.OnShowErrorMessage -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.error_message_text),
                        actionLabel = context.getString(R.string.retry_text),
                    ).let { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(NotesEvent.OnRefreshNotes)
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_note_24),
                            contentDescription = stringResource(R.string.app_name),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.onEvent(NotesEvent.OnToggleOrderSection) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                            contentDescription = stringResource(R.string.sort_notes_text),
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    actionColor = MaterialTheme.colorScheme.primary,
                    snackbarData = data,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(NotesEvent.OnCreateNote) },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = stringResource(R.string.add_note_text)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(
                visible = state.isLoading,
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surface,
                )
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
            ) {
                OrderSection(
                    noteOrder = state.noteOrder,
                    onChangeOrder = { noteOrder ->
                        viewModel.onEvent(NotesEvent.OnChangeOrder(noteOrder))
                    },
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(
                    items = state.notes,
                    key = { note -> note.id }
                ) { note ->
                    NoteItem(
                        note = note,
                        onDeleteNote = { viewModel.onEvent(NotesEvent.OnDeleteNote(note)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onEvent(NotesEvent.OnClickNote(note)) }
                            .animateItemPlacement(),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}