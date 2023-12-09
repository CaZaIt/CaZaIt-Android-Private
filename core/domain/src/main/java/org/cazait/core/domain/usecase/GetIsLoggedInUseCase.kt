package org.cazait.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.cazait.core.data.repository.AuthRepository
import org.cazait.core.data.repository.UserRepository

class GetIsLoggedInUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return userRepository.isLoggedIn()
    }
}
