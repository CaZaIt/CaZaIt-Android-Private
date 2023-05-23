package org.cazait.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.cazait.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DAOModule {

    @Singleton
    @Provides
    fun providesFavoriteCafeDAO(appDatabase: AppDatabase) = appDatabase.favoriteDAO()
}