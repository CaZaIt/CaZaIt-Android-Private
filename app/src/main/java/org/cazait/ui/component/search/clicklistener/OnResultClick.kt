package org.cazait.ui.component.search.clicklistener

import org.cazait.model.Cafe

interface OnResultClick {
    fun onResultClick(item: Cafe)
}