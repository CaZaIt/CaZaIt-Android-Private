package org.cazait.network.datasource

import org.cazait.network.NetworkConnectivity
import org.cazait.network.api.auth.CafeAuthService
import org.cazait.network.api.auth.FavoriteService
import org.cazait.network.api.unauth.CafeService
import org.cazait.network.di.Authenticated
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.model.dto.request.ListCafesReq
import org.cazait.network.model.dto.response.ListCafesRes
import org.cazait.network.model.dto.response.ListFavoritesRes
import org.cazait.network.model.dto.response.PostFavoriteCafeRes
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CafeListRemoteData @Inject constructor(
    private val cafeService: CafeService,
    @Authenticated private val cafeAuthService: CafeAuthService,
    @Authenticated private val favoriteCafeService: FavoriteService,
    private val networkConnectivity: NetworkConnectivity,
) : CafeListRemoteDataSource {
    override suspend fun getListCafes(
        userId: String,
        query: ListCafesReq
    ): DataResponse<ListCafesRes> {
        return when (val response = processCall {
            cafeAuthService.getListCafes(
                userId = userId,
                query.longitude,
                query.latitude,
                sort = query.sort,
                limit = query.limit
            )
        }) {
            is ListCafesRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun getListCafesWithGuest(query: ListCafesReq): DataResponse<ListCafesRes> {
        return when (val response = processCall {
            cafeService.getListCafesWithGuest(
                longitude = query.longitude,
                latitude = query.latitude,
                sort = query.sort,
                limit = query.limit
            )
        }) {
            is ListCafesRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun postFavoriteCafe(
        userId: String,
        cafeId: Long
    ): DataResponse<PostFavoriteCafeRes> {
        TODO("Not yet implemented")
    }

    override suspend fun getListFavoritesAuth(userId: String): DataResponse<ListFavoritesRes> {
        return when (val response = processCall {
            favoriteCafeService.getListFavoritesAuth(userId)
        }) {
            is ListFavoritesRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    override suspend fun getCafeSearch(
        cafeName: String,
        query: ListCafesReq
    ): DataResponse<ListCafesRes> {
        return when (val response = processCall {
            cafeService.getCafeSearch(
                cafeName,
                longitude = query.longitude,
                latitude = query.latitude,
                sort = query.sort,
                limit = query.limit
            )
        }) {
            is ListCafesRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(
        responseCall: suspend () -> Response<*>
    ): Any? {
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}