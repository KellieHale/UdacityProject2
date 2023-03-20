package com.udacity.asteroidradar.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate")
    suspend fun getAllSavedAsteroids(): List<Asteroid>

    @Query("SELECT * FROM asteroid WHERE closeApproachDate = :today ORDER BY closeApproachDate")
    suspend fun getAllAsteroidsToday(today: String): List<Asteroid>

    @Query("SELECT * FROM asteroid WHERE closeApproachDate IN (:dates) ORDER BY closeApproachDate")
    suspend fun getAsteroidsThisWeek(dates: List<String>): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(asteroids: List<Asteroid>)

    //-- REMOVE * FROM asteroid WHERE closeApproachDate < TODAY
    //-- @Delete
    //-- suspendFun deleteAllAsteroidsBeforeToday(date: Date)

}
