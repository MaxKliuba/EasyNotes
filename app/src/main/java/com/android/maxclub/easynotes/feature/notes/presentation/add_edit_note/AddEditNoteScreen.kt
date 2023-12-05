package com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note.components.AddEditNoteTopAppBar
import com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note.components.NoteColorSection
import com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note.components.NoteComponent

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    onNavigateUp: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val state: AddEditNoteUiState by viewModel.uiState

    Scaffold(
        topBar = {
            AddEditNoteTopAppBar(
                onNavigateUp = onNavigateUp,
                onToggleNoteColorSection = viewModel::toggleNoteColorSection,
                isNoteColorSectionEnabled = state is AddEditNoteUiState.Success,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.let { state ->
                AnimatedVisibility(
                    visible = state is AddEditNoteUiState.Loading,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.Center),
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    AnimatedVisibility(
                        visible = state is AddEditNoteUiState.Success && state.isColorSectionVisible,
                        enter = expandVertically(),
                        exit = shrinkVertically(),
                    ) {
                        if (state is AddEditNoteUiState.Success) {
                            NoteColorSection(
                                noteColor = state.note.color,
                                onChangeColor = viewModel::changeNoteColor,
                            )
                        }
                    }

                    if (state is AddEditNoteUiState.Success) {
                        Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            NoteComponent(
                                title = state.noteTitle,
                                onChangeTitle = viewModel::tryChangeNoteTitle,
                                content = state.noteContent,
                                onChangeContent = viewModel::tryChangeNoteContent,
                                noteColor = Color(state.note.color),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}