package org.cazait.core.domain.usecase

import org.cazait.core.domain.model.Message
import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.model.user.UserId
import org.cazait.core.domain.repository.UserRepository
import javax.inject.Inject

class ChangeNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: UserId, nickname: Nickname): NetworkResult<Message> {
        return userRepository.changeNickname(
            userId = userId,
            nickname = nickname,
        )
    }
}
