package com.example.congregationinfo.datasource

import com.example.congregationinfo.network.RetrofitClient
import com.example.congregationinfo.util.NetworkError
import com.example.congregationinfo.util.NetworkResult
import com.example.congregationinfo.util.NetworkSuccess


object CongregationNetworkDataSource {

    suspend fun getCongregationDataFromNetwork(): NetworkResult<Any> {
        try {
            val response =
                RetrofitClient.apiInterface.getData("AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ")
            //googleAPI.getData("AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ")

            response?.let {
                return NetworkSuccess(it.body()!!)
            }
            return NetworkError(Exception("No data"))
        }catch (e:Exception) {
            return NetworkError(e)
        }
    }
}



