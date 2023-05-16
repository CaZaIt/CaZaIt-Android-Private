package org.cazait.domain.model

import org.cazait.data.model.ListItem
import org.cazait.data.model.ViewType

data class CafeListVertical(
    val title: String = "",
    val filterState: String,
    val items: List<ListItem>
): ListItem {
    override val viewType: ViewType
        get() = ViewType.VERTICAL_FILTER
}
