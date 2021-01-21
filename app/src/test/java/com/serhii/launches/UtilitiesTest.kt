package com.serhii.launches

import com.serhii.launches.ui.launches.DateFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UtilitiesTest {

    @Test
    fun `date formatter should return formatted date`() {
        val formatter = DateFormatter()
        val unixTimeStamp = 1605287612L
        assertEquals(formatter.formatDate(unixTimeStamp), "13 Nov 2020 19:13:32")
    }
}
