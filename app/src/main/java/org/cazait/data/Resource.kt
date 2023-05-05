package org.cazait.data

const val SUCCESS = "SUCCESS"
const val FAIL = "FAIL"

sealed class Resource<T> {
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val message: String?, val data: T? = null) : Resource<T>()
}
