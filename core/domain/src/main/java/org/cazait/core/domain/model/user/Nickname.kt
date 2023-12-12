package org.cazait.core.domain.model.user

@JvmInline
value class Nickname(private val nickname: String) {
    init {
        check(nickname.isNotBlank()) { "Nickname이 빈 문자열이면 안 됩니다." }
    }

    override fun toString(): String = nickname
}
