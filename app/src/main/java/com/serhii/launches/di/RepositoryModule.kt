package com.serhii.launches.di

import com.serhii.launches.data.database.AssignmentDatabase
import com.serhii.launches.data.network.SpaceXService
import com.serhii.launches.data.repository.LaunchesRepository
import com.serhii.launches.data.repository.LaunchesRepositoryImpl
import com.serhii.launches.data.repository.RocketsRepository
import com.serhii.launches.data.repository.RocketsRepositoryImpl
import com.serhii.launches.data.source.launches.LaunchesLocalDataSource
import com.serhii.launches.data.source.launches.LaunchesLocalDataSourceImpl
import com.serhii.launches.data.source.launches.LaunchesRemoteDataSource
import com.serhii.launches.data.source.launches.LaunchesRemoteSourceImpl
import com.serhii.launches.data.source.rockets.RocketsLocalDataSource
import com.serhii.launches.data.source.rockets.RocketsLocalSourceImpl
import com.serhii.launches.data.source.rockets.RocketsRemoteDataSource
import com.serhii.launches.data.source.rockets.RocketsRemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ApplicationComponent::class)
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
        database: AssignmentDatabase,
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
        database: AssignmentDatabase,
        ioDispatcher: CoroutineDispatcher
    ): RocketsLocalDataSource {
        return RocketsLocalSourceImpl(
            database.rocketsDao(), ioDispatcher
        )
    }
}
