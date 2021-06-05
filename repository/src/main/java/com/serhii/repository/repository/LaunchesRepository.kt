package com.serhii.repository.repository

import com.serhii.repository.model.Launch
import com.serhii.repository.source.launches.LaunchesLocalDataSource
import com.serhii.repository.source.launches.LaunchesRemoteDataSource
import com.serhii.repository.Resource
import com.serhii.repository.isEmptyList
import java.lang.Exception

interface LaunchesRepository {
    suspend fun getLaunches(forceUpdate: Boolean = false): Resource<List<Launch>>
}

class LaunchesRepositoryImpl(
    private val remoteDataSource: LaunchesRemoteDataSource,
    private val localDataSource: LaunchesLocalDataSource
) : LaunchesRepository {

    override suspend fun getLaunches(forceUpdate: Boolean): Resource<List<Launch>> {

        suspend fun getFromRemote(): Resource<List<Launch>> {
            return try {
                updateLaunchesFromRemoteDataSource()
                localDataSource.getLaunches()
            } catch (ex: Exception) {
                Resource.Error(ex)
            }
        }

        return if (forceUpdate) {
            getFromRemote()
        } else {
            val launches = localDataSource.getLaunches()
            if (launches.isEmptyList) {
                getFromRemote()
            } else {
                launches
            }
        }
    }

    private suspend fun updateLaunchesFromRemoteDataSource() {
        val remoteLaunches = remoteDataSource.getLaunches()

        if (remoteLaunches is Resource.Success) {
            localDataSource.deleteLaunches()
            localDataSource.saveLaunches(remoteLaunches.data)
        } else if (remoteLaunches is Resource.Error) {
            throw remoteLaunches.exception
        }
    }
}
