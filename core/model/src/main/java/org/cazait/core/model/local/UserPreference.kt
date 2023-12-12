package org.cazait.core.model.local

import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val isLoggedIn: Boolean,
    val userId: String,
    val accountName: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun createDefaultInstance() = UserPreference(
            isLoggedIn = false,
            userId = "",
            accountName = "",
            role = "",
            accessToken = "",
            refreshToken = "",
        )
    }
}
