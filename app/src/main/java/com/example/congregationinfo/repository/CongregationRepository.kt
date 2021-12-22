package com.example.congregationinfo.repository

import com.example.congregationinfo.datasource.CongregationNetworkDataSource
import com.example.congregationinfo.util.NetworkResult


class CongregationRepository {
      suspend fun getCongregationData() : NetworkResult<Any> {
        return CongregationNetworkDataSource.getCongregationDataFromNetwork()
    }

}

