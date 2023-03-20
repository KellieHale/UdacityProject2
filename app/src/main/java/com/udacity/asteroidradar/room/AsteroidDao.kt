package com.udacity.asteroidradar.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    //-- SELECT * FROM asteroid WHERE closeApproachDate = TODAY
    @Query("SELECT * FROM asteroid")
    suspend fun getAsteroids(): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(asteroids: List<Asteroid>)

    //-- REMOVE * FROM asteroid WHERE closeApproachDate < TODAY
    //-- @Delete
    //-- suspendFun deleteAllAsteroidsBeforeToday(date: Date)

}
