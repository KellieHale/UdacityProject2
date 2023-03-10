package com.udacity.asteroidradar.api

import android.text.format.DateUtils
import com.udacity.asteroidradar.Asteroid
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NasaApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroid(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Call<ResponseBody>
}

