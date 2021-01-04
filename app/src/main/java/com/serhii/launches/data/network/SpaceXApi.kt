package com.serhii.launches.data.network

import com.serhii.launches.data.network.pojo.LaunchModel
import com.serhii.launches.data.network.pojo.RocketModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val SPACE_X_ENDPOINT_URL = "https://api.spacexdata.com/"

interface SpaceXApi {

    @GET("/v4/launches")
    suspend fun getLatestLaunchesAsync(): Response<List<LaunchModel>>

    @GET("/v4/rockets/{id}")
    suspend fun getRocketAsync(@Path("id") id: String): Response<RocketModel>
}
