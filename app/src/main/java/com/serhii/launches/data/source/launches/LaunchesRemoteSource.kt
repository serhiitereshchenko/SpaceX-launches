package com.serhii.launches.data.source.launches

import com.serhii.launches.data.model.Launch
import com.serhii.launches.data.network.SpaceXService
import com.serhii.launches.data.network.data.toLaunch
import com.serhii.launches.mvvm.Resource
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LaunchesRemoteDataSource {

    suspend fun getLaunches(): Resource<List<Launch>>
}

class LaunchesRemoteSourceImpl(
    private val apiService: SpaceXService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LaunchesRemoteDataSource {

    override suspend fun getLaunches(): Resource<List<Launch>> = withContext(ioDispatcher) {
        val response = apiService.getLatestLaunchesAsync()
        if (response.isSuccessful) {
            val launches = response.body()?.map { it.toLaunch() }
            Resource.Success(launches.orEmpty())
        } else {
            Resource.Error(IOException("Error retrieving launches from backend"))
        }
    }
}
