package com.android.maxclub.easynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.maxclub.easynotes.feature.notes.presentation.add_edit_note.AddEditNoteScreen
import com.android.maxclub.easynotes.feature.notes.presentation.notes.NotesScreen
import com.android.maxclub.easynotes.util.Screen
import com.android.maxclub.easynotes.ui.theme.EasyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyNotesTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route,
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(
                                onAddNote = {
                                    navController.navigate(Screen.AddEditNoteScreen.route)
                                },
                                onEditNote = { noteId ->
                                    navController.navigate(
                                        "${Screen.AddEditNoteScreen.route}?${Screen.AddEditNoteScreen.ARG_NOTE_ID}=$noteId"
                                    )
                                }
                            )
                        }

                        composable(
                            route = Screen.AddEditNoteScreen.routeWithArgs,
                            arguments = listOf(
                                navArgument(name = Screen.AddEditNoteScreen.ARG_NOTE_ID) {
                                    type = NavType.IntType
                                    defaultValue = Screen.AddEditNoteScreen.DEFAULT_NOTE_ID
                                },
                            ),
                        ) {
                            AddEditNoteScreen(onNavigateUp = navController::navigateUp)
                        }
                    }
                }
            }
        }
    }
}