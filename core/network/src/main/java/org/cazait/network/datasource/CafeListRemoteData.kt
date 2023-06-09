package org.cazait.network.datasource

import org.cazait.network.NetworkConnectivity
import org.cazait.network.api.CafeService
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.error.NO_INTERNET_CONNECTION
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
    private val networkConnectivity: NetworkConnectivity,
) : CafeListRemoteDataSource {
    override suspend fun getListCafes(
        userId: Long,
        query: ListCafesReq
    ): DataResponse<ListCafesRes> {
        return when (val response = processCall {
            cafeService.getListCafes(
                userId = userId,
                latitude = query.latitude,
                longitude = query.longitude,
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
        userId: Long,
        cafeId: Long
    ): DataResponse<PostFavoriteCafeRes> {
        return when (val response = processCall {
            cafeService.postFavoriteCafe(
                userId,
                cafeId,
            )
        }) {
            is PostFavoriteCafeRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
            }
        }
    }

    override suspend fun getListFavorites(userId: Long): DataResponse<ListFavoritesRes> {
        return when (val response = processCall {
            cafeService.getListFavorites(userId)
        }) {
            is ListFavoritesRes -> {
                DataResponse.Success(response)
            }

            else -> {
                DataResponse.DataError(response as Int)
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