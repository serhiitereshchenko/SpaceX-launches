package com.serhii.repository.di

import android.content.Context
import androidx.room.Room
import com.serhii.repository.database.AssignmentDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): AssignmentDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AssignmentDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
