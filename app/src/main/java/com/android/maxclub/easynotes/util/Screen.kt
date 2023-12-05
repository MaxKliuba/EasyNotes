package com.android.maxclub.easynotes.util

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes")

    object AddEditNoteScreen : Screen("add_edit_note") {
        const val ARG_NOTE_ID = "noteId" // optional
        const val DEFAULT_NOTE_ID = -1

        val routeWithArgs: String
            get() = "$route?$ARG_NOTE_ID={$ARG_NOTE_ID}"
    }
}