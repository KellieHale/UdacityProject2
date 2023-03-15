package com.udacity.asteroidradar

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfDayInterface {
    @GET("planetary/apod")
    fun getPicture(
        @Query("api_key") apiKey: String
    ): Call<ResponseBody>
}