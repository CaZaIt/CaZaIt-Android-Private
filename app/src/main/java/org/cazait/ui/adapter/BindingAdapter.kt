package org.cazait.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("visible")
fun View.setVisible(isShow: Boolean) {
    isVisible = isShow
}

@BindingAdapter("imageUrl")
fun ImageView.setImage(imageUrl: String?) {
    // `crossfade`으로 사용자 경험 개선
    load(imageUrl) {
        crossfade(300)
    }
}