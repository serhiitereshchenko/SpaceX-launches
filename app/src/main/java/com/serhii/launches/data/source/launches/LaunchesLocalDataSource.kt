package com.serhii.launches.data.source.launches

import com.serhii.launches.mvvm.Resource
import com.serhii.launches.mvvm.Resource.Error
import com.serhii.launches.mvvm.Resource.Success
import com.serhii.launches.data.database.LaunchesDao
import com.serhii.launches.data.model.Launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LaunchesLocalDataSource {

    suspend fun getLaunches(): Resource<List<Launch>>
    suspend fun getLaunchById(id: String): Resource<Launch>
    suspend fun saveLaunch(launch: Launch)
    suspend fun saveLaunches(launches: List<Launch>)
    suspend fun deleteLaunches()

}

class LaunchesLocalDataSourceImpl internal constructor(
    private val launchesDao: LaunchesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LaunchesLocalDataSource {

    override suspend fun getLaunches(): Resource<List<Launch>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(launchesDao.getLaunches())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getLaunchById(id: String): Resource<Launch> = withContext(ioDispatcher) {
        return@withContext try {
            Success(launchesDao.getLaunchById(id))
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun saveLaunch(launch: Launch) = withContext(ioDispatcher) {
        launchesDao.insertLaunch(launch)
    }

    override suspend fun saveLaunches(launches: List<Launch>) = withContext(ioDispatcher) {
        launchesDao.insertLaunches(launches)
    }

    override suspend fun deleteLaunches() {
        launchesDao.deleteAllLaunches()
    }
}
