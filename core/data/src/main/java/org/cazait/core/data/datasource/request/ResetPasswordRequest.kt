package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.Password

data class ResetPasswordRequest(
    val password: String,
) {
    constructor(password: Password) : this(password = password.toString())
}
