package com.example.congregationinfo.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {
  private const val MainServer = "https://sheets.googleapis.com"
/*
    private val client = OkHttpClient.Builder()
        .connectTimeout(20,TimeUnit.SECONDS)
        .writeTimeout(20,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .build()
*/
        private val retrofitClient: Retrofit.Builder by lazy  {

          Retrofit.Builder()
              .baseUrl(MainServer)
              .addConverterFactory(MoshiConverterFactory.create())
        }

    val apiInterface: GoogleAPI by lazy {
        retrofitClient
            .build()
            .create(GoogleAPI::class.java)
    }

}






