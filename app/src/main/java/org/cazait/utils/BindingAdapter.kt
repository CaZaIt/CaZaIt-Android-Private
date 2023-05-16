package org.cazait.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.cazait.R
import org.cazait.data.model.CafeStatus

@BindingAdapter("status")
fun TextView.setStatus(cafeStatus: CafeStatus?) {
    text = when(cafeStatus) {
        null -> context.getString(R.string.state_normal)
        CafeStatus.FREE -> context.getString(R.string.state_free)
        CafeStatus.NORMAL -> context.getString(R.string.state_normal)
        CafeStatus.CLOSE -> context.getString(R.string.state_close)
        CafeStatus.CROWDED -> context.getString(R.string.state_crowded)
        CafeStatus.VERY_CROWDED -> context.getString(R.string.state_very_crowded)
        CafeStatus.NONE -> context.getString(R.string.state_normal)
    }
}