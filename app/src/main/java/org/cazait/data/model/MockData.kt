package org.cazait.data.model

import org.cazait.data.dto.response.CafeOfCafeList
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes

object MockData {
    private val fImages = listOf(
        "isdfasdfa"
    )
    private val fList = mutableListOf<FavoriteCafe>(
        FavoriteCafe(1L, 1L, "롬곡", "광진구 군자동 32-999", "0", "0", "혼잡", fImages)
    )
    private val fData = ListFavoritesRes(
        code = 1,
        result = "SUCCESS",
        message = "SUCCESS",
        favorites = fList
    )
    private val images = listOf(
        CafeImage(1L, "sdfasdf")
    )
    private val itemCafe = CafeOfCafeList(
        1L,
        CafeStatus.CROWDED,
        "롬곡",
        "광진구 군자동 23-22222",
        longitude = "0",
        latitude = "0",
        cafesImages = images,
        distance = 0,
        favorite = false
    )
    private val list = listOf(
        itemCafe,
        itemCafe,
        itemCafe,
        itemCafe,
        itemCafe,
        itemCafe,
        itemCafe, itemCafe, itemCafe, itemCafe, itemCafe
    )
    private val data = ListCafesRes(
        code = 1,
        result = "SUCCESS",
        message = "SUCESS",
        cafes = listOf(list)
    )

    fun getMockFData() = fData
    fun getListCafesMockData() = data
}