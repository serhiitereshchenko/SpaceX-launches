package com.serhii.launches.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Keep
@Entity(tableName = "rockets")
data class Rocket @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo var id: String = UUID.randomUUID().toString(),
    @ColumnInfo var name: String = "",
    @ColumnInfo var description: String = "",
    @ColumnInfo var imageUrl: String? = null
)
