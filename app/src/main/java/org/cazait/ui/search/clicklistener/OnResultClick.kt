package org.cazait.ui.search.clicklistener

import org.cazait.model.Cafe

interface OnResultClick {
    fun onResultClick(item: Cafe)
}