package com.serhii.repository.source.launches

import com.serhii.repository.Resource
import com.serhii.repository.database.LaunchesDao
import com.serhii.repository.model.Launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

interface LaunchesLocalDataSource {

    suspend fun getLaunches(): Resource<List<Launch>>
    suspend fun getLaunchById(id: String): Resource<Launch>
    suspend fun saveLaunch(launch: Launch)
    suspend fun saveLaunches(launches: List<Launch>)
    suspend fun deleteLaunches()
}

class LaunchesLocalDataSourceImpl constructor(
    private val launchesDao: LaunchesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LaunchesLocalDataSource {

    override suspend fun getLaunches(): Resource<List<Launch>> = withContext(ioDispatcher) {
        return@withContext try {
            Resource.Success(launchesDao.getLaunches())
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun getLaunchById(id: String): Resource<Launch> = withContext(ioDispatcher) {
        return@withContext try {
            Resource.Success(launchesDao.getLaunchById(id))
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
    }

    override suspend fun saveLaunch(launch: Launch) = withContext(ioDispatcher) {
        try {
            launchesDao.insertLaunch(launch)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun saveLaunches(launches: List<Launch>) = withContext(ioDispatcher) {
        try {
            launchesDao.insertLaunches(launches)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun deleteLaunches() {
        try {
            launchesDao.deleteAllLaunches()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
