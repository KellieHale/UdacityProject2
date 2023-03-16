package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDayInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkHelper {

    companion object {
        private val interceptor = HttpLoggingInterceptor()
        private val client: OkHttpClient
        private val retrofit: Retrofit
        val nasaApiService: NasaApiService
        val pictureOfDayService: PictureOfDayInterface

        init {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .build()
            nasaApiService = retrofit.create(NasaApiService::class.java)
            pictureOfDayService = retrofit.create(PictureOfDayInterface::class.java)
        }
    }
}