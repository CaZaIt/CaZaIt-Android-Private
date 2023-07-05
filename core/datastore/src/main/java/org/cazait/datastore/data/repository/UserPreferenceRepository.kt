package org.cazait.datastore.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import org.cazait.model.local.UserPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceRepository @Inject constructor(
    private val userPreferenceDataSource: DataStore<UserPreference>
) {
    fun getUserPreference() = userPreferenceDataSource.data

    suspend fun getAccessToken(): String {
        return getUserPreference().first().accessToken
    }

    suspend fun getRefreshToken(): String {
        return getUserPreference().first().refreshToken
    }

    suspend fun updateUserPreference(
        isLoggedIn: Boolean,
        id: Long,
        email: String,
        role: String,
        accessToken: String,
        refreshToken: String
    ) {
        userPreferenceDataSource.updateData { savedUserPreferences ->
            savedUserPreferences.copy(
                isLoggedIn = isLoggedIn,
                id = id,
                email = email,
                role = role,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }

    suspend fun updateUserToken(
        accessToken: String,
        refreshToken: String,
    ) {
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
    ) {
        if (updateMode == UPDATE_JWT_TOKEN) {
            userPreferenceDataSource.updateData { savedUserPreferences ->
                savedUserPreferences.copy(
                    accessToken = token
                )
            }
        } else if (updateMode == UPDATE_REFRESH_TOKEN) {
            userPreferenceDataSource.updateData { savedUserPreferences ->
                savedUserPreferences.copy(
                    refreshToken = token
                )
            }
        }
    }

    suspend fun clearUserPreference() {
        userPreferenceDataSource.updateData {
            UserPreference.getDefaultInstance()
        }
    }

    companion object TokenType {
        const val UPDATE_JWT_TOKEN = 1
        const val UPDATE_REFRESH_TOKEN = 2
    }
}