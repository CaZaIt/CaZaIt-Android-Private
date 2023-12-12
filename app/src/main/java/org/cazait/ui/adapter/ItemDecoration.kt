package org.cazait.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(
    private val left: Int = 0,
    private val right: Int = 0,
    private val top: Int = 0,
    private val bottom: Int = 0,
    private val extraMargin: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        if (left != 0) outRect.left += left
        if (right != 0) outRect.right += right
        if (top != 0) outRect.top += top
        if (bottom != 0) outRect.bottom += bottom
        if (extraMargin != 0) {
            outRect.set(
                top + extraMargin,
                right + extraMargin,
                left + extraMargin,
                bottom + extraMargin,
            )
        }
    }
}
