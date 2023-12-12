package org.cazait.core.domain.usecase.post

import org.cazait.core.domain.model.network.NetworkResult
import org.cazait.core.domain.repository.AuthRepository
import org.cazait.core.model.sign.SignInInfo
import javax.inject.Inject

class PostSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(userId: String, password: String): NetworkResult<SignInInfo> {
        return authRepository.postSignIn(userId, password)
    }
}
