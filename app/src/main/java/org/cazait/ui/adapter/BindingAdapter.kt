package org.cazait.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import org.cazait.R
import org.cazait.model.CafeStatus

const val DURATION_CROSS_FADE = 300

@BindingAdapter("visible")
fun View.setVisible(isShow: Boolean) {
    isVisible = isShow
}

@BindingAdapter("imageUrl")
fun ImageView.setImage(imageUrl: String?) {
    // `crossfade`으로 사용자 경험 개선
    load(imageUrl) {
        crossfade(DURATION_CROSS_FADE)
    }
}

@BindingAdapter("status")
fun TextView.setStatus(cafeStatus: CafeStatus?) {
    text = when (cafeStatus) {
        null -> context.getString(R.string.state_normal)
        CafeStatus.FREE -> context.getString(R.string.state_free)
        CafeStatus.NORMAL -> context.getString(R.string.state_normal)
        CafeStatus.CLOSE -> context.getString(R.string.state_close)
        CafeStatus.CROWDED -> context.getString(R.string.state_crowded)
        CafeStatus.VERY_CROWDED -> context.getString(R.string.state_very_crowded)
        CafeStatus.NONE -> context.getString(R.string.state_normal)
    }
}

@BindingAdapter("signInStatus")
fun TextView.setSignInStatus(isLoggedIn: Boolean?) {
    text = when (isLoggedIn) {
        true -> context.getString(R.string.sign_out_kor)
        else -> context.getString(R.string.sign_in_kor)
    }
}