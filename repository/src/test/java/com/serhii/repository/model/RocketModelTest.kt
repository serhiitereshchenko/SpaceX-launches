package com.serhii.repository.model

import com.serhii.repository.network.data.RocketModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

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
