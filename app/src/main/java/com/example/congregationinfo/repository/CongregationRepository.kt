package com.example.congregationinfo.repository

import androidx.lifecycle.MutableLiveData
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.datasource.CongregationNetworkDataSource


class CongregationRepository {
      fun getCongregationData() : MutableLiveData<CongregationData> {
        return CongregationNetworkDataSource.getCongregationDataFromNetwork()
    }

}