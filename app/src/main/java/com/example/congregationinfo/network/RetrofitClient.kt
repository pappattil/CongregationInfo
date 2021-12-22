package com.example.congregationinfo.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {
  private const val MainServer = "https://sheets.googleapis.com"


      val retrofitClient: Retrofit.Builder by lazy  {
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






