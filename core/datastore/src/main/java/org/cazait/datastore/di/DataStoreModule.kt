package org.cazait.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.cazait.datastore.data.source.UserPreferenceSerializer
import org.cazait.model.local.UserPreference
import java.io.File
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserPreference> {
        return DataStoreFactory.create(
            serializer = UserPreferenceSerializer()
        ) {
            File("${context.cacheDir.path}/$PATH_PB_USER")
        }
    }

    companion object {
        // protocol buffer
        private const val PATH_PB_USER = "cazait_user.preferences_pb"
    }
}