package com.serhii.launches.data

import com.serhii.launches.data.network.data.RocketModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RocketModelTest {

    @Test(expected = IllegalArgumentException::class)
    fun `rocket model with empty id should throw exception`() {
        RocketModel("", "Launch", "", emptyList())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `rocket model with empty name should throw exception`() {
        RocketModel("1", "", "", emptyList())
    }
}
