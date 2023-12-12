package org.cazait.core.model

import java.io.Serializable

data class CafeImage(
    val cafeImageId: Long,
    val imageUrl: String,
) : Serializable
