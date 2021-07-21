package com.serhii.launches.di

import com.serhii.launches.ui.launches.DateFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UtilitiesModule {

    @ViewModelScoped
    @Provides
    fun provideDateFormatter(): DateFormatter = DateFormatter()
}
