package org.cazait.core.domain.usecase

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.repository.CafeRepository
import org.cazait.core.model.cafe.Cafes
import javax.inject.Inject

class GetCafesBySearchUseCase @Inject constructor(
    private val cafeRepository: CafeRepository,
) {
    suspend operator fun invoke(
        cafeName: String,
        latitude: Double,
        longitude: Double,
    ): NetworkResult<Cafes> {
        return cafeRepository.getCafeSearch(
            cafeName = cafeName,
            latitude = latitude.toString(),
            longitude = longitude.toString(),
        )
    }
}
