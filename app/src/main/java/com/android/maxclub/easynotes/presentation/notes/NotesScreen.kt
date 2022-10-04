package com.android.maxclub.easynotes.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.maxclub.easynotes.R
import com.android.maxclub.easynotes.presentation.notes.components.EmptyListPlaceholder
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
    val context = LocalContext.current
    val state: NotesUiState by viewModel.uiState
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is NotesUiEvent.OnCreateNote -> {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                }
                is NotesUiEvent.OnShowNote -> {
                    navController.navigate(
                        "${Screen.AddEditNoteScreen.route}?noteId=${event.note.id}"
                    )
                }
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
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.Bold,
                    )
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
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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
                        onChangeOrder = { noteOrder ->
                            viewModel.onEvent(NotesEvent.OnChangeOrder(noteOrder))
                        },
                    )
                }

                AnimatedVisibility(visible = state.notes.isNotEmpty()) {
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
                                onClickNote = { viewModel.onEvent(NotesEvent.OnClickNote(note)) },
                                onDeleteNote = { viewModel.onEvent(NotesEvent.OnDeleteNote(note)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItemPlacement(),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}