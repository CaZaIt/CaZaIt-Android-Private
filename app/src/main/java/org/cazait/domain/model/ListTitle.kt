package org.cazait.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListTitle(
    val title: String,
    var subTitle: String? = null,
): Parcelable
