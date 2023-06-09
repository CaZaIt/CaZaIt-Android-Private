package org.cazait.model.local

import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val isLoggedIn: Boolean,
    val id: Long,
    val email: String,
    val role: String,
    val jwtToken: String,
    val refreshToken: String,
) {
    companion object {
        fun getDefaultInstance() = UserPreference(
            isLoggedIn = false,
            id = -99L,
            email = "",
            role = "",
            jwtToken = "",
            refreshToken = "",
        )
    }
}
