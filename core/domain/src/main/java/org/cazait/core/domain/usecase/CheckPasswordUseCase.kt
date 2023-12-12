package org.cazait.core.domain.usecase

import org.cazait.core.domain.model.network.Message
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.repository.UserRepository
import javax.inject.Inject

class CheckPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: UserId, password: Password): NetworkResult<Message> {
        return userRepository.checkPassword(userId = userId, password = password)
    }
}
