package com.example.congregationinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.repository.CongregationRepository

class CongregationViewModel: ViewModel() {
    private var congregationRepository: CongregationRepository = CongregationRepository()

    fun getCongregationData() : LiveData<CongregationData>{
        return congregationRepository.getCongregationData()
    }
}