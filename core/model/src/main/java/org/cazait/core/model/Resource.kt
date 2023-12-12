package org.cazait.core.model

sealed interface Resource<T> {
    data class None<T>(val any: Any? = null) : Resource<T>
    data class Loading<T>(val data: T? = null) : Resource<T>
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val message: String?, val data: T? = null) : Resource<T>
}
