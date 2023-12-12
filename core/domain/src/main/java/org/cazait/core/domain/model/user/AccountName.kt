package org.cazait.core.domain.model.user

@JvmInline
value class AccountName(private val accountName: String) {
    init {
        check(accountName.isNotBlank()) { "AccountName은 공백을 허용하지 않습니다." }
    }

    override fun toString(): String = accountName
}
