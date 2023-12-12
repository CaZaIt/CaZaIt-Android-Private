package org.cazait.ui.search.clicklistener

import org.cazait.core.model.cafe.Cafe

interface OnResultClick {
    fun onResultClick(item: Cafe)
}
