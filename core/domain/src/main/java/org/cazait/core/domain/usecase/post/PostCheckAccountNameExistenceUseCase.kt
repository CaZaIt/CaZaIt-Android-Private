package org.cazait.core.domain.usecase.post

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.ExistenceStatus
import javax.inject.Inject

class PostCheckAccountNameExistenceUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(accountName: String): NetworkResult<ExistenceStatus> {
        return userRepository.postCheckAccountNameExistence(
            accountName = accountName,
            isExist = "false",
        )
    }
}
