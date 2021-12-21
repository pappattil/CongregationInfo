package com.example.congregationinfo.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.congregationinfo.data.CongregationData
import com.example.congregationinfo.repository.CongregationRepository
import com.example.congregationinfo.util.NetworkError
import com.example.congregationinfo.util.NetworkSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CongregationViewModel: ViewModel() {
    private var congregationRepository: CongregationRepository = CongregationRepository()

    private val result = MutableLiveData<CongregationViewState>()

    fun getCongregationLiveData()=result

    fun getCongregationData() {
        result.value = inProgress

        viewModelScope.launch(Dispatchers.IO){
            val response = congregationRepository.getCongregationData()
            when(response){
                is NetworkSuccess -> {
                    val congresult = response.result as CongregationData
                    result.postValue(congregationResponseSuccess(congresult))
                }
                is NetworkError -> {
                    result.postValue(congregationResponseError(response.errorMessage.message!!))
                }
            }
        }
    }
}

