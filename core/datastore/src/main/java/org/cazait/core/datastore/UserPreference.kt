package org.cazait.core.datastore

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserPreference(
    val isLoggedIn: Boolean,
    val userId: String,
    val accountName: String,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
) {
    init {
        runCatching { UUID.fromString(userId) }
            .onFailure { error("UserPreference의 userId는 UUID 형식이어야 한다.") }
    }

    companion object {
        fun createDefaultInstance() = UserPreference(
            isLoggedIn = false,
            userId = UUID.randomUUID().toString(),
            accountName = "",
            role = "",
            accessToken = "",
            refreshToken = "",
        )
    }
}
