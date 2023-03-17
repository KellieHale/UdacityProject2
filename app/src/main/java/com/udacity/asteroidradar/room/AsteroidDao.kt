package com.udacity.asteroidradar.room

import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {

    //-- SELECT * FROM asteroid WHERE closeApproachDate >= TODAY
    @Query("SELECT * FROM asteroid")
    suspend fun getAsteroids(): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(asteroids: List<Asteroid>)
}
