package com.udacity.asteroidradar.api

import android.text.format.DateUtils
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService {
    @GET("asteroid")
    suspend fun getAsteroid(
        @Query("start_date") startDate: DateUtils,
        @Query("end_date") endDate: DateUtils,
        @Query("api_key") apiKey: String
    ): Response<List<Asteroid>>
}

object NasaApi {
    val retrofitService : NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}