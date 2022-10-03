package com.android.maxclub.easynotes.presentation.add_edit_note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.maxclub.easynotes.R
import com.android.maxclub.easynotes.presentation.add_edit_note.components.NoteComponent

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val state: AddEditNoteUiState by viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(R.string.navigate_up_text),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.onEvent(AddEditNoteEvent.OnColorSectionToggle) },
                        enabled = state.isColorSectionEnabled,
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
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.let { state ->
                when (state) {
                    is AddEditNoteUiState.Loading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                    is AddEditNoteUiState.Success -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            NoteComponent(
                                title = state.note.title,
                                onChangeTitle = { title ->
                                    viewModel.onEvent(AddEditNoteEvent.OnChangeTitle(title))
                                },
                                content = state.note.content,
                                onChangeContent = { content ->
                                    viewModel.onEvent(AddEditNoteEvent.OnChangeContent(content))
                                },
                                noteColor = Color(state.note.color),
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}