package org.cazait.data.model

data class CafeListVertical(
    val title: String = "",
    val filterState: String,
    val items: List<ListItem>
): ListItem {
    override val viewType: ViewType
        get() = ViewType.VERTICAL_FILTER
}
