package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NetworkHelper
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.PictureOfDayInterface
import com.udacity.asteroidradar.api.parsePictureOfTheDayResult
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

    fun getAsteroids() {
        val dates = getNextSevenDaysFormattedDates()
        NetworkHelper.retrofitService.getAsteroid(dates[0], dates[dates.size - 1], Constants.API_KEY).enqueue( object:
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val jsonObject = JSONObject(it.string())
                    val asteroids = parseAsteroidsJsonResult(jsonObject)
                    _asteroids.value = asteroids
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getPictureOfDay() {
        NetworkHelper.retrofitServ.getPicture(Constants.API_KEY).enqueue( object:
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let {
                    val jsonObject = JSONObject(it.string())
                    val pictureOfDay = parsePictureOfTheDayResult(jsonObject)
                    _photo.value = pictureOfDay
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }
        )
    }

}

