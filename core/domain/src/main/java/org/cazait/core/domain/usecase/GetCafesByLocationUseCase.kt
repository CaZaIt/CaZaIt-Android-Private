package org.cazait.core.domain.usecase

import org.cazait.core.domain.model.location.Latitude
import org.cazait.core.domain.model.location.Longitude
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.repository.CafeRepository
import org.cazait.core.model.cafe.Cafes
import javax.inject.Inject

class GetCafesByLocationUseCase @Inject constructor(
    private val cafeRepository: CafeRepository,
) {
    suspend operator fun invoke(latitude: Latitude, longitude: Longitude): NetworkResult<Cafes> {
        return cafeRepository.getListCafes(
            latitude = latitude,
            longitude = longitude,
        )
    }
}
