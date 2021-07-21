package com.serhii.repository.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.UUID

@Keep
@Entity(tableName = "launches")
data class Launch @JvmOverloads constructor(
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "date") var rawDate: Long = 1L,
    @ColumnInfo(name = "success") var isSuccess: Boolean = false,
    @ColumnInfo(name = "rocket_id") var rocketId: String = "",
    @PrimaryKey @ColumnInfo(name = "entry_id") var id: String = UUID.randomUUID().toString(),
    @Ignore var formattedDate: String = ""
)
