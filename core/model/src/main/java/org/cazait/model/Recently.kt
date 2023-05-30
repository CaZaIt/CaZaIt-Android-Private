package org.cazait.model

data class Recently(

    var name: String,
    val address: String,
    val distance: Int,
    val latitude: String? = null,
    val imageUrl: List<String>
)
