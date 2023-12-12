package org.cazait.core.model.local

import kotlinx.serialization.Serializable

@Serializable
data class UserPreference(
    val isLoggedIn: Boolean,
    val uuid: String,
    val userId: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun createDefaultInstance() = UserPreference(
            isLoggedIn = false,
            uuid = "",
            userId = "",
            role = "",
            accessToken = "",
            refreshToken = "",
        )
    }
}
