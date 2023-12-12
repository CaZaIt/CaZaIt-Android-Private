package org.cazait.ui.cafeinfo.detail.clicklistener

import org.cazait.core.model.CafeReview

interface ReviewItemClick {
    fun onEditClick(item: CafeReview)
    fun onDeleteClick(item: CafeReview)
    fun onReportClick(item: CafeReview)
}
