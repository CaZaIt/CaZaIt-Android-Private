package org.cazait.data.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.api.CafeService
import org.cazait.data.error.ErrorManager
import org.cazait.data.error.NO_INTERNET_CONNECTION
import org.cazait.data.model.Empty
import org.cazait.data.model.request.ListCafesReq
import org.cazait.data.model.response.ListCafesRes
import org.cazait.data.model.response.ListFavoritesRes
import org.cazait.network.NetworkConnectivity
import java.io.IOException
import javax.inject.Inject

class CafeListRemoteData @Inject constructor(
    private val cafeService: CafeService,
    private val networkConnectivity: NetworkConnectivity,
    private val errorManager: ErrorManager,
) : CafeListRemoteDataSource {
    override suspend fun getListCafes(userId: Long, query: ListCafesReq): Resource<ListCafesRes> {
        if (!networkConnectivity.isConnected()) {
            val errorMessage = errorManager.getError(NO_INTERNET_CONNECTION).toString()
            return Resource.Error(errorMessage)
        }

        return try {
            val response = cafeService.getListCafes(
                userId,
                longitude = query.longitude,
                latitude = query.latitude,
                sort = query.sort,
                limit = query.limit
            ).execute()

            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }

    override suspend fun getListCafesWithGuest(query: ListCafesReq): Resource<ListCafesRes> {
        if (!networkConnectivity.isConnected()) {
            val errorMessage = errorManager.getError(NO_INTERNET_CONNECTION).toString()
            return Resource.Error(errorMessage)
        }

        return try {
            val response = cafeService.getListCafesWithGuest(
                longitude = query.longitude,
                latitude = query.latitude,
                sort = query.sort,
                limit = query.limit
            ).execute()

            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }

    override suspend fun getListFavorites(userId: Long): Resource<ListFavoritesRes> {
        if (!networkConnectivity.isConnected()) {
            val errorMessage = errorManager.getError(NO_INTERNET_CONNECTION).toString()
            return Resource.Error(errorMessage)
        }

        return try {
            val response = cafeService.getListFavorites(userId).execute()
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.message)
        }
    }
}