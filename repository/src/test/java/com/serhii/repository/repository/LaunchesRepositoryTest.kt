package com.serhii.repository.repository

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.serhii.repository.Resource
import com.serhii.repository.source.launches.LaunchesLocalDataSource
import com.serhii.repository.source.launches.LaunchesRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LaunchesRepositoryTest {

    @Mock
    private lateinit var remoteDataSource: LaunchesRemoteDataSource

    @Mock
    private lateinit var localDataSource: LaunchesLocalDataSource

    private lateinit var launchesRepository: LaunchesRepository

    @Before
    fun setup() {
        launchesRepository = LaunchesRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `update from remote storage when force update is true`(): Unit = runBlocking {
        launchesRepository.getLaunches(forceUpdate = true)
        verify(remoteDataSource).getLaunches()
    }

    @Test
    fun `update from remote storage when local storage empty force update is false`(): Unit = runBlocking {
        given(localDataSource.getLaunches()).willReturn(Resource.Success(emptyList()))
        launchesRepository.getLaunches(forceUpdate = false)
        verify(remoteDataSource).getLaunches()
    }

    @Test
    fun `update from local storage when force update is false`(): Unit = runBlocking {
        launchesRepository.getLaunches(forceUpdate = false)
        verify(remoteDataSource, never()).getLaunches()
        verify(localDataSource).getLaunches()
    }
}
