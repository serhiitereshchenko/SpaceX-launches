package com.serhii.launches.data

import com.serhii.launches.data.network.data.LaunchModel
import com.serhii.launches.data.network.data.UNKNOWN_DATE
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LaunchesModelTest {

    @Test(expected = IllegalArgumentException::class)
    fun `launch model with empty id should throw exception`() {
        LaunchModel("", "Launch", true, UNKNOWN_DATE, "1")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `launch model with empty name should throw exception`() {
        LaunchModel("1", "", true, UNKNOWN_DATE, "1")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `launch model with empty rocket id should throw exception`() {
        LaunchModel("1", "Launch", true, UNKNOWN_DATE, "")
    }
}
