package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.Password

class ResetPasswordRequest private constructor(
    val password: String,
) {
    companion object {
        fun of(password: Password): ResetPasswordRequest = ResetPasswordRequest(password.toString())
    }
}
