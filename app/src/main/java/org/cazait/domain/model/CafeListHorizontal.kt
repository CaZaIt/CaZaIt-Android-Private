package org.cazait.domain.model

import org.cazait.data.model.ListItem

data class CafeListHorizontal(
    val title: String,
    val subtitle: String,
    val items: List<ListItem>
)
