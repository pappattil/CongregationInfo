package com.example.congregationinfo.repository

import androidx.lifecycle.MutableLiveData
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.datasource.CongregationNetworkDataSource
import com.example.congregationinfo.ui.CongregationViewState


class CongregationRepository {
      fun getCongregationData() : MutableLiveData<CongregationViewState> {
        return CongregationNetworkDataSource.getCongregationDataFromNetwork()
    }

}