package com.serhii.repository.source.rockets

import com.serhii.repository.model.Rocket
import com.serhii.repository.network.SpaceXService
import com.serhii.repository.Resource
import com.serhii.repository.network.data.toRocket
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RocketsRemoteDataSource {

    suspend fun getRocketById(id: String): Resource<Rocket>
}

class RocketsRemoteSourceImpl(
    private val apiService: SpaceXService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RocketsRemoteDataSource {

    override suspend fun getRocketById(id: String): Resource<Rocket> = withContext(ioDispatcher) {
        val response = apiService.getRocketAsync(id)
        if (response.isSuccessful) {
            val rocket = response.body()?.toRocket()
            if (rocket != null) {
                Resource.Success(rocket)
            } else {
                Resource.Error(IOException("Rocket is null"))
            }
        } else {
            Resource.Error(IOException("Error retrieving rocket from cloud"))
        }
    }
}
