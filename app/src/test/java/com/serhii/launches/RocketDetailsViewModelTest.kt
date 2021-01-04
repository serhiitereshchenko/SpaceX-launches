package com.serhii.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.serhii.launches.data.model.Rocket
import com.serhii.launches.data.repository.RocketsRepository
import com.serhii.launches.data.repository.RocketsRepositoryImpl
import com.serhii.launches.data.source.rockets.RocketsLocalDataSource
import com.serhii.launches.data.source.rockets.RocketsRemoteDataSource
import com.serhii.launches.mvvm.Resource
import com.serhii.launches.ui.rocket_details.RocketDetailsViewModel
import com.serhii.launches.rules.TestCoroutineRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.never
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RocketDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var rocketsRepository: RocketsRepository
    private lateinit var viewModel: RocketDetailsViewModel

    @Mock
    private lateinit var rocketsRemoteDataSource: RocketsRemoteDataSource
    @Mock
    private lateinit var rocketsLocalDataSource: RocketsLocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        rocketsRepository = RocketsRepositoryImpl(rocketsRemoteDataSource, rocketsLocalDataSource)

        viewModel = RocketDetailsViewModel(rocketsRepository)
    }

    @Test
    fun `update from remote storage when force update flag is true`(): Unit = runBlocking {
        viewModel.loadRocket(ROCKET.id, true)
        verify(rocketsRemoteDataSource).getRocketById(ROCKET.id)
    }

    @Test
    fun `update from local storage when force update flag is false and has local data`(): Unit = runBlocking {
        rocketsLocalDataSource.stub {
            onBlocking { getRocketById(ROCKET.id) }.doReturn(Resource.Success(ROCKET))
        }
        viewModel.loadRocket(ROCKET.id, false)
        verify(rocketsRemoteDataSource, never()).getRocketById(ROCKET.id)
        verify(rocketsLocalDataSource).getRocketById(ROCKET.id)
    }

    companion object {
        val ROCKET = Rocket()
    }
}
