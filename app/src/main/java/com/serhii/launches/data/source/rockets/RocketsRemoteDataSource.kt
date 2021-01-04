package com.serhii.launches.data.source.rockets

import com.serhii.launches.data.model.Rocket
import com.serhii.launches.data.network.SpaceXService
import com.serhii.launches.data.network.pojo.toRocket
import com.serhii.launches.mvvm.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

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
            Resource.Error(IOException("Error retrieving rocket from backend"))
        }
    }
}
