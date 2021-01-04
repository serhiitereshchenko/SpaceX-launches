package com.serhii.launches.di

import com.serhii.launches.ui.launches.DateFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class UtilitiesModule {

    @Singleton
    @Provides
    fun provideDateFormatter(): DateFormatter = DateFormatter()

}
