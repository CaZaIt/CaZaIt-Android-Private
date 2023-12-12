package org.cazait.datastore.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import org.cazait.core.model.local.UserPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceRepository @Inject constructor(
    private val userPreferenceDataSource: DataStore<UserPreference>,
) {
    fun getUserPreference(): Flow<UserPreference> = userPreferenceDataSource.data

    suspend fun updateUserPreference(
        isLoggedIn: Boolean,
        uuid: String,
        userId: String,
        role: String,
        accessToken: String,
        refreshToken: String,
    ) = runCatching {
        userPreferenceDataSource.updateData { savedUserPreferences ->
            savedUserPreferences.copy(
                isLoggedIn = isLoggedIn,
                userId = uuid,
                accountName = userId,
                role = role,
                accessToken = accessToken,
                refreshToken = refreshToken,
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
                refreshToken = refreshToken,
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
                    accessToken = token,
                )
            }
        } else {
            userPreferenceDataSource.updateData { savedUserPreferences ->
                savedUserPreferences.copy(
                    refreshToken = token,
                )
            }
        }
    }

    suspend fun clearUserPreference() = runCatching {
        userPreferenceDataSource.updateData {
            UserPreference.createDefaultInstance()
        }
    }

    companion object TokenType {
        const val UPDATE_JWT_TOKEN = 1
        const val UPDATE_REFRESH_TOKEN = 2
    }
}
