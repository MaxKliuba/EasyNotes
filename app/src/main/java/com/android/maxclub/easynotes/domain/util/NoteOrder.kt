package com.android.maxclub.easynotes.domain.util

sealed class NoteOrder(val isDescending: Boolean) {
    class ByTimestamp(isDescending: Boolean = true) : NoteOrder(isDescending)
    class ByColor(isDescending: Boolean = false) : NoteOrder(isDescending)
    class ByTitle(isDescending: Boolean = false) : NoteOrder(isDescending)

    fun copy(isDescending: Boolean = this.isDescending): NoteOrder =
        when (this) {
            is ByTimestamp -> ByTimestamp(isDescending)
            is ByTitle -> ByTitle(isDescending)
            is ByColor -> ByColor(isDescending)
        }
}
