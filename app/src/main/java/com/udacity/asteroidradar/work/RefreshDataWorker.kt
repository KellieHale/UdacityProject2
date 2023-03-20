package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NetworkHelper
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.room.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.await

class RefreshDataWorker(private val appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val dates = getNextSevenDaysFormattedDates()
        val responseBody = NetworkHelper.nasaApiService.getAsteroid(dates[0], dates[dates.size -1], Constants.API_KEY).await()
        val jsonObject = JSONObject(responseBody.string())
        val asteroids = parseAsteroidsJsonResult(jsonObject)
        saveAsteroids(asteroids)
        return try {
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    private fun saveAsteroids(asteroids: List<Asteroid>) {
        GlobalScope.launch(Dispatchers.IO) {
            val asteroidsDb = AsteroidDatabase.getInstance(appContext)
            asteroidsDb.asteroidDao().insertAllAsteroids(asteroids)
        }
    }
}