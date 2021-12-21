package com.example.congregationinfo.ui

import com.example.congregationinfo.data.CongregationData


sealed class CongregationViewState

object inProgress:CongregationViewState()
data class congregationResponseSuccess(val data : CongregationData): CongregationViewState()
data class congregationResponseError(val exceptionMSG: String): CongregationViewState()