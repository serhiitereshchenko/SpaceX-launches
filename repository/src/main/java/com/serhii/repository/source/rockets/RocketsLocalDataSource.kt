package com.serhii.repository.source.rockets

import com.serhii.repository.database.RocketsDao
import com.serhii.repository.model.Rocket
import com.serhii.repository.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RocketsLocalDataSource {

    suspend fun getRocketById(id: String): Resource<Rocket>

    suspend fun saveRocket(rocket: Rocket)
}

class RocketsLocalSourceImpl constructor(
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
