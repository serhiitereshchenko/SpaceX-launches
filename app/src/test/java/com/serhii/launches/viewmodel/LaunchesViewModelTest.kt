package com.serhii.launches.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.serhii.launches.rules.TestCoroutineRule
import com.serhii.launches.ui.launches.DateFormatter
import com.serhii.launches.ui.launches.FilterCriteria
import com.serhii.launches.ui.launches.LaunchesViewModel
import com.serhii.repository.Resource
import com.serhii.repository.model.Launch
import com.serhii.repository.repository.LaunchesRepository
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LaunchesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var launchesRepository: LaunchesRepository
    private lateinit var viewModel: LaunchesViewModel
    private val dateFormatter: DateFormatter = DateFormatter()

    @Before
    fun setup() {
        viewModel = LaunchesViewModel(launchesRepository, dateFormatter)
    }

    @Test
    fun `return cached items when remote storage throws error`(): Unit = runBlocking {
        given(launchesRepository.getLaunches(forceUpdate = true)).willReturn(Resource.Error(IOException()))
        viewModel.loadLaunches(forceUpdate = true)
        verify(launchesRepository).getLaunches(forceUpdate = false)
    }

    @Test
    fun `return succeed items when filter criteria success`(): Unit = runBlocking {
        given(launchesRepository.getLaunches()).willReturn(
            Resource.Success(
                listOf(
                    Launch(id = "1", isSuccess = false),
                    Launch(id = "2", isSuccess = true),
                    Launch(id = "3", isSuccess = true),
                    Launch(id = "4", isSuccess = false),
                    Launch(id = "5", isSuccess = true)
                )
            )
        )
        viewModel.filterCriteria = FilterCriteria.SuccessLaunch
        viewModel.loadLaunches(forceUpdate = false)
        val launches = (viewModel.launches.value as? Resource.Success)?.data.orEmpty()

        assertEquals(3, launches.size)
        assertTrue(launches.any { it.id == "2" })
        assertTrue(launches.any { it.id == "3" })
        assertTrue(launches.any { it.id == "5" })
    }

    @Test
    fun `return all items when filter criteria null`(): Unit = runBlocking {
        val allLaunches = listOf(
            Launch(id = "1", isSuccess = false),
            Launch(id = "2", isSuccess = true),
            Launch(id = "3", isSuccess = true),
            Launch(id = "4", isSuccess = false),
            Launch(id = "5", isSuccess = true)
        )
        given(launchesRepository.getLaunches()).willReturn(
            Resource.Success(allLaunches)
        )
        viewModel.filterCriteria = null
        viewModel.loadLaunches(forceUpdate = false)
        val launches = (viewModel.launches.value as? Resource.Success)?.data.orEmpty()

        assertEquals(5, launches.size)
        assertSame(launches, allLaunches)
    }
}
