package org.cazait.core.domain.model.user

@JvmInline
value class Nickname(private val nickname: String) {
    init {
        check(nickname.isNotBlank()) { "Nickname이 빈 문자열이면 안 됩니다." }
        check(nickname.length > NICKNAME_LENGTH_MIN) { "Nickname은 최소 3글자 이상이어야 합니다."}
        check(nickname.length < NICKNAME_LENGTH_MAX) { "Nickname은 최소 8글자 미만이어야 합니다."}
    }

    override fun toString(): String = nickname

    companion object {
        const val NICKNAME_LENGTH_MAX = 8
        const val NICKNAME_LENGTH_MIN = 2
    }
}
