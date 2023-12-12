package org.cazait.core.domain.model.user

import java.util.UUID

@JvmInline
value class UserId(private val userId: UUID) {
    fun toUUID(): UUID = userId
}
