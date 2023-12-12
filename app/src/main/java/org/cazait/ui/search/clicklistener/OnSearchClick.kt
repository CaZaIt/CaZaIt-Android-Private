package org.cazait.ui.search.clicklistener

import org.cazait.core.model.cafe.Cafe

interface OnSearchClick {
    fun onSearchClick(item: Cafe)
}
