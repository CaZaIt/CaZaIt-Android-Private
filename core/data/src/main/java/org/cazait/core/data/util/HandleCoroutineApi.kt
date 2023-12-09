package org.cazait.core.data.util

import org.cazait.core.data.model.network.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T : Any> handleApi(
    response: Response<T>,
): NetworkResult<T> {
    return try {
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.ApiSuccess(body)
        } else {
            NetworkResult.ApiError(code = response.code(), error = response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.ApiError(code = e.code(), error = e.message())
    } catch (e: IOException) {
        NetworkResult.ApiException(e)
    }
}
