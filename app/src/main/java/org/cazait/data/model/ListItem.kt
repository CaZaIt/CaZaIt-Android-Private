package org.cazait.data.model

import java.io.Serializable

interface ListItem: Serializable {
    val viewType: ViewType

    fun getKey() = hashCode()
}

enum class ViewType {
    HORIZONTAL,
    VERTICAL,
    VERTICAL_FILTER,
    IMAGE,

    EMPTY,
}