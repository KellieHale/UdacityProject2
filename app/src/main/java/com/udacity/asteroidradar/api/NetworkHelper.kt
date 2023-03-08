package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkHelper {
    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()

        val retrofitService : NasaApiService by lazy {
            retrofit.create(NasaApiService::class.java)
        }
    }
}