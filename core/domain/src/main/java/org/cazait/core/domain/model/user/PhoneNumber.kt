package org.cazait.core.domain.model.user

@JvmInline
value class PhoneNumber(private val phoneNumber: String) {
    init {
        check(phoneNumber.length < 12) { "Invalid phone number length" }
    }
    override fun toString(): String = phoneNumber
}