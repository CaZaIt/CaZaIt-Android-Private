package org.cazait.core.domain.model.cafe

import java.util.UUID

@JvmInline
value class CafeId(private val cafeId: UUID) {
    fun toUUID(): UUID = cafeId
}
