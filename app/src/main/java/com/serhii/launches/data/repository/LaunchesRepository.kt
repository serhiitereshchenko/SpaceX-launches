package com.serhii.launches.data.repository

import com.serhii.launches.data.model.Launch
import com.serhii.launches.data.source.launches.LaunchesLocalDataSource
import com.serhii.launches.data.source.launches.LaunchesRemoteDataSource
import com.serhii.launches.mvvm.Resource
import com.serhii.launches.mvvm.isEmptyList
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
