package org.cazait.core.domain.usecase

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.repository.AuthRepository
import org.cazait.core.model.token.RefreshToken
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): NetworkResult<RefreshToken> {
        return authRepository.getRefreshToken()
    }
}