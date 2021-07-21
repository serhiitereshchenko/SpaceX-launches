package com.serhii.repository.repository

import com.serhii.repository.model.Rocket
import com.serhii.repository.source.rockets.RocketsLocalDataSource
import com.serhii.repository.source.rockets.RocketsRemoteDataSource
import com.serhii.repository.Resource
import com.serhii.repository.hasData
import com.serhii.repository.isNullData
import timber.log.Timber

interface RocketsRepository {
    suspend fun getRocket(id: String, forceUpdate: Boolean = false): Resource<Rocket>
}

class RocketsRepositoryImpl(
    private val remoteDataSource: RocketsRemoteDataSource,
    private val localDataSource: RocketsLocalDataSource
) : RocketsRepository {

    override suspend fun getRocket(id: String, forceUpdate: Boolean): Resource<Rocket> {
        try {
            if (forceUpdate || localDataSource.getRocketById(id).isNullData) {
                updateFromRemoteSource(id)
            }
        } catch (e: Exception) {
            Timber.e(e)
            return Resource.Error(e)
        }
        return localDataSource.getRocketById(id)
    }

    private suspend fun updateFromRemoteSource(id: String) {
        val rocketResource = remoteDataSource.getRocketById(id)
        if (rocketResource.hasData) {
            (rocketResource as Resource.Success).data?.let { localDataSource.saveRocket(it) }
        } else if (rocketResource is Resource.Error) {
            throw rocketResource.exception
        }
    }
}
