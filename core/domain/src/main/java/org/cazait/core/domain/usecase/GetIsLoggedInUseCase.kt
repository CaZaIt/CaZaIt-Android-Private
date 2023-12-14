package org.cazait.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.cazait.core.domain.repository.UserRepository
import javax.inject.Inject

class GetIsLoggedInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return userRepository.isLoggedIn()
    }
}
