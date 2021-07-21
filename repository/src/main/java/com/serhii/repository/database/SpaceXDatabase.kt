package com.serhii.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serhii.repository.model.Launch
import com.serhii.repository.model.Rocket

@Database(entities = [Launch::class, Rocket::class], version = 1, exportSchema = false)
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun launchesDao(): LaunchesDao

    abstract fun rocketsDao(): RocketsDao
}
