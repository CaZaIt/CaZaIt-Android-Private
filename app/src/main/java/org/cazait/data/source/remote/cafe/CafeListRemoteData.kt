package org.cazait.data.source.remote.cafe

import android.util.Log
import org.cazait.data.api.CafeService
import org.cazait.data.error.ErrorManager
import org.cazait.data.error.NO_INTERNET_CONNECTION
import org.cazait.data.dto.response.ListCafesRes
import org.cazait.data.dto.response.ListFavoritesRes
import org.cazait.data.dto.request.ListCafesReq
import org.cazait.data.dto.response.PostFavoriteCafeRes
import org.cazait.data.dto.response.DataResponse
import org.cazait.data.error.NETWORK_ERROR
import org.cazait.network.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CafeListRemoteData @Inject constructor(
    private val cafeService: CafeService,
    private val networkConnectivity: NetworkConnectivity,
    private val errorManager: ErrorManager,
) : CafeListRemoteDataSource {
    override suspend fun getListCafes(
        userId: Long,
        query: ListCafesReq
    ): DataResponse<ListCafesRes> {
        return when (val response = processCall {
            cafeService.getListCafes(
                userId,
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

    override suspend fun getListCafesWithGuest(query: ListCafesReq): DataResponse<ListCafesRes> {
        return when(val response = processCall {
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
        return when(val response = processCall {
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
        return when(val response = processCall {
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
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("processCall", "$responseCode")
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}