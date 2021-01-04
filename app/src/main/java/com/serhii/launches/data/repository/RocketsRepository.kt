package com.serhii.launches.data.repository

import com.serhii.launches.data.model.Rocket
import com.serhii.launches.data.source.rockets.RocketsLocalDataSource
import com.serhii.launches.data.source.rockets.RocketsRemoteDataSource
import com.serhii.launches.mvvm.Resource
import com.serhii.launches.mvvm.isNullData

interface RocketsRepository {
    suspend fun getRocket(id: String, forceUpdate: Boolean = false): Resource<Rocket>
}

class RocketsRepositoryImpl(
    private val remoteDataSource: RocketsRemoteDataSource,
    private val localDataSource: RocketsLocalDataSource
) : RocketsRepository {

    override suspend fun getRocket(id: String, forceUpdate: Boolean): Resource<Rocket> {

        suspend fun getFromRemote(): Resource<Rocket> {
            return try {
                updateRocketFromRemoteStorage(id)
                localDataSource.getRocketById(id)
            } catch (ex: Exception) {
                Resource.Error(ex)
            }
        }

        return if (forceUpdate) {
            getFromRemote()
        } else {
            val resource = localDataSource.getRocketById(id)
            if (resource.isNullData) {
                getFromRemote()
            } else {
                resource
            }
        }
    }

    private suspend fun updateRocketFromRemoteStorage(id: String) {
        val rocketResource = remoteDataSource.getRocketById(id)
        if (rocketResource is Resource.Success) {
            localDataSource.saveRocket(rocketResource.data)
        } else if (rocketResource is Resource.Error) {
            throw rocketResource.exception
        }
    }
}
