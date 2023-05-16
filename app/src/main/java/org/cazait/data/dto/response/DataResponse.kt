package org.cazait.data.dto.response

sealed class DataResponse<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : DataResponse<T>(data)
    class DataError<T>(errorCode: Int) : DataResponse<T>(null, errorCode)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode]"
        }
    }
}
