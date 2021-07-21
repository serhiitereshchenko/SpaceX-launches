package com.serhii.launches.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.serhii.launches.rules.TestCoroutineRule
import com.serhii.launches.ui.launches.DateFormatter
import com.serhii.launches.ui.launches.LaunchesViewModel
import com.serhii.repository.Resource
import com.serhii.repository.model.Launch
import com.serhii.repository.repository.LaunchesRepository
import com.serhii.repository.repository.LaunchesRepositoryImpl
import com.serhii.repository.source.launches.LaunchesLocalDataSource
import com.serhii.repository.source.launches.LaunchesRemoteDataSource
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
class LaunchesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var launchesRemoteDataSource: LaunchesRemoteDataSource
    @Mock
    private lateinit var launchesLocalDataSource: LaunchesLocalDataSource

    private lateinit var launchesRepository: LaunchesRepository
    private lateinit var viewModel: LaunchesViewModel

    private val dateFormatter: DateFormatter = DateFormatter()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        launchesRepository =
            LaunchesRepositoryImpl(launchesRemoteDataSource, launchesLocalDataSource)

        viewModel = LaunchesViewModel(launchesRepository, dateFormatter)
    }

    @Test
    fun `update from remote storage when force update flag is true`(): Unit = runBlocking {
        viewModel.loadLaunches(true)
        verify(launchesRemoteDataSource).getLaunches()
    }

    @Test
    fun `update from local storage when force update flag is false and has local data`(): Unit =
        runBlocking {
            launchesLocalDataSource.stub {
                onBlocking { getLaunches() }.doReturn(Resource.Success(listOf(Launch())))
            }
            viewModel.loadLaunches(false)
            verify(launchesRemoteDataSource, never()).getLaunches()
            verify(launchesLocalDataSource).getLaunches()
        }
}
