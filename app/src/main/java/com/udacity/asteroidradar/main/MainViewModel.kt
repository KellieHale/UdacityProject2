package com.udacity.asteroidradar.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NetworkHelper
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parsePictureOfTheDayResult
import com.udacity.asteroidradar.room.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _photo = MutableLiveData<PictureOfDay>()
    val photo: LiveData<PictureOfDay>
        get() = _photo

    var currentFilter: AsteroidApiFilter = AsteroidApiFilter.SHOW_WEEK

    fun updateUi(context: Context) {
        getFilteredAsteroids(context)
        getPictureOfDay()
    }

    private fun getPictureOfDay() {
        NetworkHelper.pictureOfDayService.getPicture(Constants.API_KEY).enqueue( object:
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val jsonObject = JSONObject(it.string())
                    val pictureOfDay = parsePictureOfTheDayResult(jsonObject)
                    _photo.value = pictureOfDay
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun updateFilter(filter: AsteroidApiFilter) {
        this.currentFilter = filter
    }

    private fun saveAsteroids(context: Context, asteroids: List<Asteroid>) {
        GlobalScope.launch(Dispatchers.IO) {
            val asteroidsDb = AsteroidDatabase.getInstance(context)
            asteroidsDb.asteroidDao().insertAllAsteroids(asteroids)
            getFilteredAsteroids(context)
        }
    }

    fun getFilteredAsteroids(context: Context) {
        when (currentFilter) {
            AsteroidApiFilter.SHOW_SAVED -> {
                showSavedAsteroids(context)
            }
            AsteroidApiFilter.SHOW_TODAY -> {
                showAsteroidsForToday(context)
            }
            AsteroidApiFilter.SHOW_WEEK -> {
                showAsteroidsForWeek(context)
            }
        }
    }

    private fun showSavedAsteroids(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val asteroidsDb = AsteroidDatabase.getInstance(context)
            val asteroids = asteroidsDb.asteroidDao().getAllSavedAsteroids()
            launch(Dispatchers.Main) { _asteroids.value = asteroids }
        }
    }

    private fun showAsteroidsForToday(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val asteroidsDb = AsteroidDatabase.getInstance(context)
            val asteroids = asteroidsDb.asteroidDao().getAllAsteroidsToday(
                getNextSevenDaysFormattedDates()[0]
            )
            launch(Dispatchers.Main) { _asteroids.value = asteroids }
        }
    }

    private fun showAsteroidsForWeek(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val asteroidsDb = AsteroidDatabase.getInstance(context)
            val asteroids = asteroidsDb.asteroidDao().getAsteroidsThisWeek(
                getNextSevenDaysFormattedDates()
            )
            launch(Dispatchers.Main) { _asteroids.value = asteroids }
        }
    }
}
enum class AsteroidApiFilter {
    SHOW_SAVED,
    SHOW_TODAY,
    SHOW_WEEK
}


