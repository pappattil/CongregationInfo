package com.example.congregationinfo.datasource

import androidx.lifecycle.MutableLiveData
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.network.RetrofitClient
import com.example.congregationinfo.ui.CongregationViewState
import com.example.congregationinfo.ui.congregationResponseError
import com.example.congregationinfo.ui.congregationResponseSuccess
import com.example.congregationinfo.ui.inProgress
import com.example.congregationinfo.util.NetworkError
import com.example.congregationinfo.util.NetworkResult
import com.example.congregationinfo.util.NetworkSuccess
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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



