package com.serhii.repository.response

import com.serhii.repository.fromJson
import com.serhii.repository.network.data.RocketModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class RocketResponseTest {

    @Test
    fun `test RocketResponse`() {
        val rocket = fromJson<RocketModel>("GetRocketResponse")

        assertNotNull(rocket)

        assertEquals(
            4,
            rocket.images.size
        )
        assertEquals(
            "https://farm5.staticflickr.com/4599/38583829295_581f34dd84_b.jpg",
            rocket.images[0]
        )
        assertEquals(
            "https://farm5.staticflickr.com/4645/38583830575_3f0f7215e6_b.jpg",
            rocket.images[1]
        )
        assertEquals(
            "https://farm5.staticflickr.com/4696/40126460511_b15bf84c85_b.jpg",
            rocket.images[2]
        )
        assertEquals(
            "https://farm5.staticflickr.com/4711/40126461411_aabc643fd8_b.jpg",
            rocket.images[3]
        )
        assertEquals(
            "Falcon Heavy",
            rocket.name
        )
        assertEquals(
            "With the ability to lift into orbit over 54 metric tons (119,000 lb)--a mass equivalent to a 737 jetliner loaded with passengers, crew, luggage and fuel--Falcon Heavy can lift more than twice the payload of the next closest operational vehicle, the Delta IV Heavy, at one-third the cost.",
            rocket.description
        )
        assertEquals(
            "5e9d0d95eda69974db09d1ed",
            rocket.id
        )
    }
}
