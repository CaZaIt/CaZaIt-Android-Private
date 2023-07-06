package org.cazait.model.local

import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val isLoggedIn: Boolean,
    val id: String,
    val email: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun getDefaultInstance() = UserPreference(
            isLoggedIn = false,
            id = "",
            email = "",
            role = "",
            accessToken = "",
            refreshToken = "",
        )
    }
}
