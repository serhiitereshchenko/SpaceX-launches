package com.serhii.repository.repository

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.serhii.repository.Resource
import com.serhii.repository.model.Rocket
import com.serhii.repository.source.rockets.RocketsLocalDataSource
import com.serhii.repository.source.rockets.RocketsRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RocketRepositoryTest {

    private lateinit var rocketsRepository: RocketsRepository

    @Mock
    private lateinit var remoteDataSource: RocketsRemoteDataSource

    @Mock
    private lateinit var localDataSource: RocketsLocalDataSource

    private val rocket: Rocket = Rocket()

    @Before
    fun setup() {
        rocketsRepository = RocketsRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `update from remote storage when force update is true`(): Unit = runBlocking {
        rocketsRepository.getRocket(rocket.id, true)
        verify(remoteDataSource).getRocketById(rocket.id)
    }

    @Test
    fun `update from remote storage when local storage empty force update is false`(): Unit = runBlocking {
        given(localDataSource.getRocketById(rocket.id)).willReturn(Resource.Success(null))
        rocketsRepository.getRocket(rocket.id, forceUpdate = false)
        verify(remoteDataSource).getRocketById(rocket.id)
    }

    @Test
    fun `update from local storage when force update is false`(): Unit = runBlocking {
        rocketsRepository.getRocket(rocket.id, false)
        verify(remoteDataSource, BDDMockito.never()).getRocketById(rocket.id)
        verify(localDataSource).getRocketById(rocket.id)
    }
}
