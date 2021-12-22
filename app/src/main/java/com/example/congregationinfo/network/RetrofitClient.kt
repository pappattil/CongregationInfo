package com.example.congregationinfo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {
  private const val MainServer = "https://sheets.googleapis.com"
    private val okhttp = OkHttpClient()

      private val retrofitClient: Retrofit.Builder by lazy  {
          okhttp.callTimeoutMillis.times(60)
          okhttp.connectTimeoutMillis.times(60)
          Retrofit.Builder()
              .baseUrl(MainServer)
              .client(okhttp)
              .addConverterFactory(MoshiConverterFactory.create())
        }

    val apiInterface: GoogleAPI by lazy {
        retrofitClient
            .build()
            .create(GoogleAPI::class.java)
    }

}






