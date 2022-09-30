package com.android.maxclub.easynotes.domain.util

sealed class NoteOrder(val direction: OrderDirection) {
    class ByTimestamp(direction: OrderDirection) : NoteOrder(direction)
    class ByTitle(direction: OrderDirection) : NoteOrder(direction)
    class ByColor(direction: OrderDirection) : NoteOrder(direction)
}
