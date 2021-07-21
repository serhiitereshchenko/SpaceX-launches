package com.serhii.repository.source.rockets

import com.serhii.repository.model.Rocket
import com.serhii.repository.network.SpaceXService
import com.serhii.repository.Resource
import com.serhii.repository.network.data.toRocket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

interface RocketsRemoteDataSource {

    suspend fun getRocketById(id: String): Resource<Rocket>
}

class RocketsRemoteSourceImpl(
    private val apiService: SpaceXService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RocketsRemoteDataSource {

    override suspend fun getRocketById(id: String): Resource<Rocket> = withContext(ioDispatcher) {
        try {
            val rocketModel = apiService.getRocketAsync(id)
            Resource.Success(rocketModel.toRocket())
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }
}
