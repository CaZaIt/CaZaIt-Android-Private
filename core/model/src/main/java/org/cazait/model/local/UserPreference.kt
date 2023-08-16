package org.cazait.model.local

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
        fun getDefaultInstance() = UserPreference(
            isLoggedIn = false,
            uuid = "",
            userId = "",
            role = "",
            accessToken = "",
            refreshToken = "",
        )
    }
}
