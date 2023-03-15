package com.udacity.asteroidradar.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDayInterface
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkHelper {

    companion object {
        private val interceptor = HttpLoggingInterceptor()
        private val client: OkHttpClient
        private val retrofit: Retrofit
        val retrofitService: NasaApiService
        val retrofitServ: PictureOfDayInterface

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
            retrofitService = retrofit.create(NasaApiService::class.java)
            retrofitServ = retrofit.create(PictureOfDayInterface::class.java)
        }
    }
}