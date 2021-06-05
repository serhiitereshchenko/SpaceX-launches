package com.serhii.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serhii.repository.model.Launch

@Dao
interface LaunchesDao {

    @Query("SELECT * FROM launches")
    suspend fun getLaunches(): List<Launch>

    @Query("SELECT * FROM launches WHERE entry_id = :id")
    suspend fun getLaunchById(id: String): Launch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launch: Launch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<Launch>)

    @Query("DELETE FROM launches")
    suspend fun deleteAllLaunches()
}
