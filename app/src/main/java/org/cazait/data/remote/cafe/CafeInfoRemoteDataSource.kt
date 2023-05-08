package org.cazait.data.remote.cafe

import org.cazait.data.Resource
import org.cazait.data.model.response.CafeMenuRes

interface CafeInfoRemoteDataSource {
    suspend fun getMenus(cafeId: Long): Resource<CafeMenuRes>
}