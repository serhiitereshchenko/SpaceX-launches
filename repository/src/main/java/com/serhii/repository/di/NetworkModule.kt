package com.serhii.repository.di

import com.serhii.repository.network.SPACE_X_ENDPOINT_URL
import com.serhii.repository.network.SpaceXApi
import com.serhii.repository.network.SpaceXService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(SPACE_X_ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideSpaceXApi(retrofit: Retrofit): SpaceXApi = retrofit.create(SpaceXApi::class.java)

    @Singleton
    @Provides
    fun provideSpaceXService(api: SpaceXApi): SpaceXService = SpaceXService(api)
}
