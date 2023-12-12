package org.cazait.core.domain.usecase

import org.cazait.core.domain.model.Message
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.repository.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: UserId, password: Password): NetworkResult<Message> {
        return userRepository.changePassword(userId = userId, password = password)
    }
}