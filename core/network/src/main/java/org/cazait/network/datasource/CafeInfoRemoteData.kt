package org.cazait.network.datasource

import org.cazait.network.model.dto.response.CafeMenuRes
import org.cazait.network.model.dto.response.CafeReviewRes
import org.cazait.network.NetworkConnectivity
import org.cazait.network.error.NETWORK_ERROR
import org.cazait.network.error.NO_INTERNET_CONNECTION
import org.cazait.network.model.dto.DataResponse
import org.cazait.network.api.CafeService
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CafeInfoRemoteData @Inject constructor(
    private val cafeService: CafeService,
    private val networkConnectivity: NetworkConnectivity,
) : CafeInfoRemoteDataSource {

    override suspend fun getMenus(cafeId: Long): DataResponse<CafeMenuRes> {
        return when (val response = processCall {
            cafeService.getMenus(
                cafeId = cafeId
            )
        }) {
            is CafeMenuRes -> {
                DataResponse.Success(data = response)
            }

            else -> {
                DataResponse.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): DataResponse<CafeReviewRes> {
        return when (val response = processCall {
            cafeService.getReviews(
                cafeId, sortBy, score, lastId
            )
        }) {
            is CafeReviewRes -> {
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
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
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