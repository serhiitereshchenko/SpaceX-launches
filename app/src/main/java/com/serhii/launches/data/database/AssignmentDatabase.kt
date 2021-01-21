package com.serhii.launches.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serhii.launches.data.model.Launch
import com.serhii.launches.data.model.Rocket

@Database(entities = [Launch::class, Rocket::class], version = 1, exportSchema = false)
abstract class AssignmentDatabase : RoomDatabase() {

    abstract fun launchesDao(): LaunchesDao

    abstract fun rocketsDao(): RocketsDao
}
