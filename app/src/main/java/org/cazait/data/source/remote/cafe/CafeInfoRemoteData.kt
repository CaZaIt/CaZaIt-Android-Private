package org.cazait.data.source.remote.cafe

import org.cazait.domain.model.Resource
import org.cazait.data.api.CafeService
import org.cazait.data.error.ErrorManager
import org.cazait.data.error.NO_INTERNET_CONNECTION
import org.cazait.data.dto.response.CafeMenuRes
import org.cazait.data.dto.response.CafeReviewRes
import org.cazait.network.NetworkConnectivity
import java.io.IOException
import javax.inject.Inject

class CafeInfoRemoteData @Inject constructor(
    private val cafeService: CafeService,
    private val networkConnectivity: NetworkConnectivity,
    private val errorManager: ErrorManager
) : CafeInfoRemoteDataSource {

    override suspend fun getMenus(cafeId: Long): Resource<CafeMenuRes> {
        if (!networkConnectivity.isConnected()) {
            val errorMessage = errorManager.getError(NO_INTERNET_CONNECTION).toString()
            return Resource.Error(errorMessage)
        }

        return try {
            val response = cafeService.getMenus(cafeId = cafeId).execute()
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getReviews(
        cafeId: Long,
        sortBy: String?,
        score: Int?,
        lastId: Long?
    ): Resource<CafeReviewRes> {
        if (!networkConnectivity.isConnected()) {
            val errorMessage = errorManager.getError(NO_INTERNET_CONNECTION).toString()
            return Resource.Error(errorMessage)
        }

        return try {
            val response = cafeService.getReviews(cafeId, sortBy, score, lastId).execute()
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }
}