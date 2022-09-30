package com.android.maxclub.easynotes.domain.util

sealed class OrderDirection {
    object Ascending : OrderDirection()
    object Descending : OrderDirection()
}
