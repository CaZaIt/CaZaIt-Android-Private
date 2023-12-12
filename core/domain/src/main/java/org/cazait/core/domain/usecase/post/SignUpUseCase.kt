package org.cazait.core.domain.usecase.post

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.model.user.AccountName
import org.cazait.core.domain.model.user.Password
import org.cazait.core.domain.repository.UserRepository
import org.cazait.core.model.SignUpInfo
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        accountName: AccountName,
        password: Password,
        phoneNumber: String,
        nickname: String,
    ): NetworkResult<SignUpInfo> {
        return userRepository.signUp(
            accountName = accountName,
            password = password,
            phoneNumber = phoneNumber,
            nickname = nickname,
        )
    }
}
