package com.serhii.repository.repository

import com.serhii.repository.model.Launch
import com.serhii.repository.source.launches.LaunchesLocalDataSource
import com.serhii.repository.source.launches.LaunchesRemoteDataSource
import com.serhii.repository.Resource
import com.serhii.repository.hasData
import com.serhii.repository.isEmptyList
import timber.log.Timber

interface LaunchesRepository {
    suspend fun getLaunches(forceUpdate: Boolean = false): Resource<List<Launch>>
}

class LaunchesRepositoryImpl(
    private val remoteDataSource: LaunchesRemoteDataSource,
    private val localDataSource: LaunchesLocalDataSource
) : LaunchesRepository {

    override suspend fun getLaunches(forceUpdate: Boolean): Resource<List<Launch>> {
        try {
            if (forceUpdate || localDataSource.getLaunches().isEmptyList) {
                updateFromRemoteSource()
            }
        } catch (e: Exception) {
            Timber.e(e)
            return Resource.Error(e)
        }
        return localDataSource.getLaunches()
    }

    private suspend fun updateFromRemoteSource() {
        val remoteLaunches = remoteDataSource.getLaunches()
        if (remoteLaunches.hasData) {
            localDataSource.deleteLaunches()
            (remoteLaunches as Resource.Success).data?.let { localDataSource.saveLaunches(it) }
        } else if (remoteLaunches is Resource.Error) {
            throw remoteLaunches.exception
        }
    }
}
