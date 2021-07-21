package com.serhii.repository.di

import android.content.Context
import androidx.room.Room
import com.serhii.repository.database.SpaceXDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "SpaceXLaunches.db"

@InstallIn(SingletonComponent::class)
@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SpaceXDatabase {
        return Room.databaseBuilder(
            context,
            SpaceXDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
