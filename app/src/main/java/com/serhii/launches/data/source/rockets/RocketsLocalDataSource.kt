package com.serhii.launches.data.source.rockets

import com.serhii.launches.data.database.RocketsDao
import com.serhii.launches.data.model.Rocket
import com.serhii.launches.mvvm.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RocketsLocalDataSource {

    suspend fun getRocketById(id: String): Resource<Rocket>

    suspend fun saveRocket(rocket: Rocket)
}

class RocketsLocalSourceImpl internal constructor(
    private val rocketsDao: RocketsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RocketsLocalDataSource {

    override suspend fun getRocketById(id: String): Resource<Rocket> = withContext(ioDispatcher) {
        return@withContext try {
            Resource.Success(rocketsDao.getRocketById(id))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun saveRocket(rocket: Rocket) = withContext(ioDispatcher) {
        rocketsDao.insertRocket(rocket)
    }
}
