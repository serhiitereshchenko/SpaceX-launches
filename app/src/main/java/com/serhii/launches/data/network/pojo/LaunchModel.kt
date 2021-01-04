package com.serhii.launches.data.network.pojo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.serhii.launches.data.model.Launch

@Keep
data class LaunchModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("date_unix") val dateUnix: Long,
    @SerializedName("rocket") val rocketId: String
)

fun LaunchModel.toLaunch(): Launch =
    Launch(
        name = name,
        date = dateUnix,
        isSuccess = success,
        id = id,
        rocketId = rocketId
    )
