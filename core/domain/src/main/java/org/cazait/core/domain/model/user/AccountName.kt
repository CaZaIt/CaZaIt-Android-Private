package org.cazait.core.domain.model.user

@JvmInline
value class AccountName(val value: String) {
    init {
        check(value.isNotBlank()) { "AccountName은 공백을 허용하지 않습니다." }
    }
}
