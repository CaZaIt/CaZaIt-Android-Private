package org.cazait.core.data.datasource.request

import org.cazait.core.domain.model.user.Password

class CheckPasswordRequest(
    val password: String,
) {
    constructor(password: Password) : this(password = password.toString())
}
