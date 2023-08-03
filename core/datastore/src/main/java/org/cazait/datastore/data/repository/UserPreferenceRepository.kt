package org.cazait.datastore.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import org.cazait.model.local.UserPreference
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceRepository @Inject constructor(
    private val userPreferenceDataSource: DataStore<UserPreference>
) {
    fun getUserPreference() = userPreferenceDataSource.data

    suspend fun updateUserPreference(
        isLoggedIn: Boolean,
        uuid: String,
        userId: String,
        role: String,
        accessToken: String,
        refreshToken: String
    ) = runCatching {
        userPreferenceDataSource.updateData { savedUserPreferences ->
            savedUserPreferences.copy(
                isLoggedIn = isLoggedIn,
                uuid = uuid,
                userId = userId,
                role = role,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }

    suspend fun updateUserToken(
        accessToken: String,
        refreshToken: String,
    ) = runCatching {
        userPreferenceDataSource.updateData { savedUserPreferences ->
            savedUserPreferences.copy(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }

    suspend fun updateUserToken(
        token: String,
        updateMode: Int,
    ) = runCatching {
        if (updateMode == UPDATE_JWT_TOKEN) {
            userPreferenceDataSource.updateData { savedUserPreferences ->
                savedUserPreferences.copy(
                    accessToken = token
                )
            }
        } else {
            userPreferenceDataSource.updateData { savedUserPreferences ->
                savedUserPreferences.copy(
                    refreshToken = token
                )
            }
        }
    }

    suspend fun clearUserPreference() = runCatching {
        userPreferenceDataSource.updateData {
            UserPreference.getDefaultInstance()
        }
    }

    companion object TokenType {
        const val UPDATE_JWT_TOKEN = 1
        const val UPDATE_REFRESH_TOKEN = 2
    }
}