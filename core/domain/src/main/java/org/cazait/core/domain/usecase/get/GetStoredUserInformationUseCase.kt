package org.cazait.core.domain.usecase.get

import kotlinx.coroutines.flow.Flow
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.local.UserPreference
import javax.inject.Inject

class GetStoredUserInformationUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Flow<UserPreference> = userRepository.getUserInfo()
}
