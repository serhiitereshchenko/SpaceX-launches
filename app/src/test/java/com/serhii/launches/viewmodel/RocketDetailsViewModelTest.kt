package com.serhii.launches.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.serhii.launches.rules.TestCoroutineRule
import com.serhii.launches.ui.rocket_details.RocketDetailsViewModel
import com.serhii.repository.Resource
import com.serhii.repository.model.Rocket
import com.serhii.repository.repository.RocketsRepository
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RocketDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var rocketsRepository: RocketsRepository

    private lateinit var viewModel: RocketDetailsViewModel
    private val rocket: Rocket = Rocket()

    @Before
    fun setup() {
        viewModel = RocketDetailsViewModel(rocketsRepository)
    }

    @Test
    fun `return cached rocket when remote storage throws error`(): Unit = runBlocking {
        given(rocketsRepository.getRocket(rocket.id, forceUpdate = true)).willReturn(Resource.Error(IOException()))
        viewModel.loadRocket(rocket.id, forceUpdate = true)
        verify(rocketsRepository).getRocket(rocket.id, forceUpdate = false)
    }
}
