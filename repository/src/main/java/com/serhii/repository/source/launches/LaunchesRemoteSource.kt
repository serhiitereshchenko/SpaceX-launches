package com.serhii.repository.source.launches

import com.serhii.repository.model.Launch
import com.serhii.repository.network.SpaceXService
import com.serhii.repository.Resource
import com.serhii.repository.network.data.toLaunch
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
            Resource.Error(IOException("Error retrieving launches from cloud"))
        }
    }
}
