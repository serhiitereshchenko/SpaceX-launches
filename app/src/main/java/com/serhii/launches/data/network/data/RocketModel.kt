package com.serhii.launches.data.network.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.serhii.launches.data.model.Rocket

@Keep
data class RocketModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("flickr_images") val images: List<String>
) {
    init {
        require(id.isNotEmpty()) { "Rocket id cannot be empty" }
        require(name.isNotEmpty()) { "Rocket name cannot be empty" }
    }
}

fun RocketModel.toRocket(): Rocket = Rocket(
    id = id,
    name = name,
    description = description,
    imageUrl = images.firstOrNull()
)
