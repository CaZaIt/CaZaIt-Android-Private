package org.cazait.data.model

data class Cafe(
    val cafeId: Long,
    val name: String,
    val address: String,
    val distance: Int,
    val status: String,
    val images: List<CafeImage>
)
