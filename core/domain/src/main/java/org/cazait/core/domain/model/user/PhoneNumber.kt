package org.cazait.core.domain.model.user

@JvmInline
value class PhoneNumber(
    private val phoneNumber: String,
) {
    override fun toString(): String = phoneNumber
}
