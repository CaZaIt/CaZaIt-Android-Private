package org.cazait.core.data.datasource.request

import com.google.gson.annotations.SerializedName

data class ListCafesRequest(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("sort")
    val sort: String = "distance",
    @SerializedName("limit")
    val limit: String = "0",
)
