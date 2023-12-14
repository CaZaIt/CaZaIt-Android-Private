package org.cazait.core.domain.model.user

@JvmInline
value class Nickname(
    private val nickname: String,
) {
    override fun toString(): String = nickname
}
