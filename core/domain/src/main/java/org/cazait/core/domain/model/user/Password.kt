package org.cazait.core.domain.model.user

@JvmInline
value class Password(val value: String) {
    init {
        check(value.isNotBlank()) { "Password는 공백을 허용하지 않습니다." }
    }
}
