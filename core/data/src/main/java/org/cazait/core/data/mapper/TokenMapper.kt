package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.RefreshTokenResponse
import org.cazait.core.model.token.RefreshToken

internal fun RefreshTokenResponse.toData() = RefreshToken(
    refreshToken = this.data.refreshToken,
)
