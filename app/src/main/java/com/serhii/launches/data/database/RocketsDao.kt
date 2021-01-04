package com.serhii.launches.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serhii.launches.data.model.Rocket

@Dao
interface RocketsDao {

    @Query("SELECT * FROM rockets WHERE id = :id")
    suspend fun getRocketById(id: String): Rocket

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocket(rocket: Rocket)
}
