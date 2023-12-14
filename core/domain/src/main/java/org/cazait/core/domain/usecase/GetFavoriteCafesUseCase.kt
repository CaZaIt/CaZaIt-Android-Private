package org.cazait.core.domain.usecase

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.repository.CafeRepository
import org.cazait.core.model.cafe.FavoriteCafes
import javax.inject.Inject

class GetFavoriteCafesUseCase @Inject constructor(
    private val cafeRepository: CafeRepository,
) {
    suspend operator fun invoke(userId: UserId): NetworkResult<FavoriteCafes> =
        cafeRepository.getListFavoritesAuth(userId)
}
