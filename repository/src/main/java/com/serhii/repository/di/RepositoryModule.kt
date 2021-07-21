package com.serhii.repository.di

import com.serhii.repository.database.SpaceXDatabase
import com.serhii.repository.network.SpaceXService
import com.serhii.repository.repository.LaunchesRepository
import com.serhii.repository.repository.LaunchesRepositoryImpl
import com.serhii.repository.repository.RocketsRepository
import com.serhii.repository.repository.RocketsRepositoryImpl
import com.serhii.repository.source.launches.LaunchesLocalDataSource
import com.serhii.repository.source.launches.LaunchesLocalDataSourceImpl
import com.serhii.repository.source.launches.LaunchesRemoteDataSource
import com.serhii.repository.source.launches.LaunchesRemoteSourceImpl
import com.serhii.repository.source.rockets.RocketsLocalDataSource
import com.serhii.repository.source.rockets.RocketsLocalSourceImpl
import com.serhii.repository.source.rockets.RocketsRemoteDataSource
import com.serhii.repository.source.rockets.RocketsRemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideLaunchesRepository(
        remoteDataSource: LaunchesRemoteDataSource,
        localDataSource: LaunchesLocalDataSource
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(
            remoteDataSource, localDataSource
        )
    }

    @Singleton
    @Provides
    fun provideLaunchesRemoteDataSource(apiService: SpaceXService): LaunchesRemoteDataSource {
        return LaunchesRemoteSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideLaunchesLocalDataSource(
        database: SpaceXDatabase,
        ioDispatcher: CoroutineDispatcher
    ): LaunchesLocalDataSource {
        return LaunchesLocalDataSourceImpl(
            database.launchesDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideRocketsRepository(
        remoteDataSource: RocketsRemoteDataSource,
        localDataSource: RocketsLocalDataSource
    ): RocketsRepository {
        return RocketsRepositoryImpl(
            remoteDataSource, localDataSource
        )
    }

    @Singleton
    @Provides
    fun provideRocketsRemoteDataSource(apiService: SpaceXService): RocketsRemoteDataSource {
        return RocketsRemoteSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideRocketsLocalDataSource(
        database: SpaceXDatabase,
        ioDispatcher: CoroutineDispatcher
    ): RocketsLocalDataSource {
        return RocketsLocalSourceImpl(
            database.rocketsDao(), ioDispatcher
        )
    }
}
