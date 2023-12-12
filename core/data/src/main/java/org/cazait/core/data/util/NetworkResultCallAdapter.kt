package org.cazait.core.data.util

import kotlinx.coroutines.CoroutineScope
import org.cazait.core.domain.model.network.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter(
    private val resultType: Type,
    private val coroutineScope: CoroutineScope,
) : CallAdapter<Type, Call<NetworkResult<Type>>> {
    override fun responseType(): Type = resultType
    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> =
        NetworkResultCall(call, coroutineScope)
}
