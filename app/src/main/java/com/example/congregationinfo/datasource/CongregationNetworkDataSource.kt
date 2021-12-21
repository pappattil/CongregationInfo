package com.example.congregationinfo.datasource

import androidx.lifecycle.MutableLiveData
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.network.RetrofitClient
import com.example.congregationinfo.ui.CongregationViewState
import com.example.congregationinfo.ui.congregationResponseError
import com.example.congregationinfo.ui.congregationResponseSuccess
import com.example.congregationinfo.ui.inProgress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object CongregationNetworkDataSource {

    fun getCongregationDataFromNetwork(): MutableLiveData<CongregationViewState> {
        val call = RetrofitClient.apiInterface.getData("AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ")
        //googleAPI.getData("AIzaSyA4gXS5fL81WnHO98SiwsmBGiYb9V1ceQQ")

        val congregationResultData = MutableLiveData<CongregationViewState>()
        congregationResultData.value=inProgress

        call.enqueue(object: Callback<CongregationData> {
            override fun onResponse(
                call: Call<CongregationData>,
                response: Response<CongregationData>
            ) {
                congregationResultData.value= congregationResponseSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<CongregationData>, t: Throwable) {
                congregationResultData.value= congregationResponseError(t.message.toString())
            }
        })
        return  congregationResultData
    }
}