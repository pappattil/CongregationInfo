package com.example.congregationinfo.ui.ui

import com.example.congregationinfo.data.CongregationJsonData


sealed class CongregationViewState

object inProgress: CongregationViewState()
data class congregationResponseSuccess(val data : CongregationJsonData): CongregationViewState()
data class congregationResponseError(val exceptionMSG: String): CongregationViewState()