package com.serhii.repository.response

import com.serhii.repository.fromJson
import com.serhii.repository.network.data.LaunchModel
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull


class LaunchesResponseTest {

    @Test
    fun `test LaunchesResponse`() {
        val launches = fromJson<List<LaunchModel>>("GetLaunchesResponse")

        assertNotNull(launches)
        assertEquals(3, launches.size)

        assertEquals("rocket1", launches[0].rocketId)
        assertEquals("rocket1_id", launches[0].id)
        assertEquals(true, launches[0].success)
        assertEquals("rocket1_ship", launches[0].name)
        assertEquals(1L, launches[0].dateUnix)

        assertEquals("rocket2", launches[1].rocketId)
        assertEquals("rocket2_id", launches[1].id)
        assertEquals(false, launches[1].success)
        assertEquals("rocket2_ship", launches[1].name)
        assertEquals(2L, launches[1].dateUnix)

        assertEquals("rocket3", launches[2].rocketId)
        assertEquals("rocket3_id", launches[2].id)
        assertEquals(true, launches[2].success)
        assertEquals("rocket3_ship", launches[2].name)
        assertEquals(3L, launches[2].dateUnix)
    }
}
