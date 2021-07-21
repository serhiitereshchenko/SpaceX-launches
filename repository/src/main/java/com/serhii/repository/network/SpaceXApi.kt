package com.serhii.repository.network

import com.serhii.repository.network.data.LaunchModel
import com.serhii.repository.network.data.RocketModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val SPACE_X_ENDPOINT_URL = "https://api.spacexdata.com/"

interface SpaceXApi {

    @GET("/v4/launches")
    suspend fun getLaunchesAsync(): Response<List<LaunchModel>>

    @GET("/v4/rockets/{id}")
    suspend fun getRocketAsync(@Path("id") id: String): Response<RocketModel>
}
