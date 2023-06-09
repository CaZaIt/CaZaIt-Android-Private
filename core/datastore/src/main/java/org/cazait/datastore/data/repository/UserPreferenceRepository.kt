package org.cazait.datastore.data.repository

import androidx.datastore.core.DataStore
import org.cazait.model.local.UserPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceRepository @Inject constructor(
    private val userPreferenceDataSource: DataStore<UserPreference>
) {
    fun getUserPreference() = userPreferenceDataSource.data

    suspend fun updateUserPreference(
        id: Long,
        email: String,
        role: String,
        jwtToken: String,
        refreshToken: String
    ) {
        userPreferenceDataSource.updateData { savedUserPreferences ->
            savedUserPreferences.copy(
                id = id,
                email = email,
                role = role,
                jwtToken = jwtToken,
                refreshToken = refreshToken
            )
        }
    }

    suspend fun updateUserToken(
        jwtToken: String,
        refreshToken: String,
    ) {
        userPreferenceDataSource.updateData { savedUserPreferences ->
            savedUserPreferences.copy(
                jwtToken = jwtToken,
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
                    jwtToken = token
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