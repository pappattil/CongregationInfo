package com.example.congregationinfo.ui.ui

import com.example.congregationinfo.data.CongregationJsonData


sealed class CongregationViewState

object InProgress: CongregationViewState()
data class CongregationResponseSuccess(val data : CongregationJsonData): CongregationViewState()
data class CongregationResponseError(val exceptionMSG: String): CongregationViewState()