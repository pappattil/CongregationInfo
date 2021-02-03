package com.example.congregationinfo.network


import com.example.congregationinfo.data.CongregationData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//URL:
//https://sheets.googleapis.com/v4/spreadsheets/13vaiSoZkld08eLdHqtNcFQdwjJxFm7tXAZu4KviuI-I/values/0!B2:F105?key=AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ

//PATH:
///v4/spreadsheets/13vaiSoZkld08eLdHqtNcFQdwjJxFm7tXAZu4KviuI-I/values/0!B2:F105


//KEYS:
//?key=AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ

interface GoogleAPI {
    @GET("/v4/spreadsheets/13vaiSoZkld08eLdHqtNcFQdwjJxFm7tXAZu4KviuI-I/values/0!B2:F105")
    fun getData(@Query("key") key: String): Call<CongregationData>

}