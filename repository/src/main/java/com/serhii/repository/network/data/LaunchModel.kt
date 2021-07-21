package com.serhii.repository.network.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.serhii.repository.model.Launch

const val UNKNOWN_DATE = -1L

@Keep
data class LaunchModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("success") val success: Boolean?,
    @SerializedName("date_unix") val dateUnix: Long?,
    @SerializedName("rocket") val rocketId: String
) {
    init {
        require(id.isNotEmpty()) { "Launch id cannot be empty" }
        require(rocketId.isNotEmpty()) { "Rocket id cannot be empty" }
        require(name.isNotEmpty()) { "Name cannot be empty" }
    }
}

fun LaunchModel.toLaunch(): Launch = Launch(
    name = name,
    rawDate = dateUnix ?: UNKNOWN_DATE,
    isSuccess = success ?: false,
    id = id,
    rocketId = rocketId
)
