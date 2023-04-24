package org.cazait.data.model.request

data class ListCafesReq(
    val latitude: String,
    val longitude: String,
    val sort: String = "distance",
    val limit: String = "0"
)