package org.cazait.core.domain.usecase.post

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.Nickname
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.ExistenceStatus
import javax.inject.Inject

class PostCheckNicknameExistenceUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(nickname: Nickname): NetworkResult<ExistenceStatus> {
        return userRepository.postCheckNicknameExistence(nickname = nickname, isExist = "false")
    }
}
