package com.example.congregationinfo.repository

import androidx.lifecycle.MutableLiveData
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.datasource.CongregationNetworkDataSource
import com.example.congregationinfo.ui.CongregationViewState
import com.example.congregationinfo.util.NetworkResult


class CongregationRepository {
      suspend fun getCongregationData() : NetworkResult<Any> {
        return CongregationNetworkDataSource.getCongregationDataFromNetwork()
    }

}

