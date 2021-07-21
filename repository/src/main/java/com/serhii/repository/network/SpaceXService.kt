package com.serhii.repository.network

import com.serhii.repository.network.data.LaunchModel
import com.serhii.repository.network.data.RocketModel
import java.io.IOException

class SpaceXService(private val api: SpaceXApi) {

    suspend fun getLaunchesAsync(): List<LaunchModel> {
        val response = api.getLaunchesAsync()
        if (response.isSuccessful) {
            return response.body().orEmpty()
        } else {
            throw IOException("Error during retrieving launches: ${response.message()}")
        }
    }

    suspend fun getRocketAsync(id: String): RocketModel {
        val response = api.getRocketAsync(id)
        if (response.isSuccessful) {
            return requireNotNull(response.body()) { "Rocket info can't be null" }
        } else {
            throw IOException("Error during retrieving rocket info: ${response.message()}")
        }
    }
}
