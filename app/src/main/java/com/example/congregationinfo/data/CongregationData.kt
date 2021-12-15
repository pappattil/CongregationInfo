package com.example.congregationinfo.data

import com.squareup.moshi.JsonClass

// result generated from /json
@JsonClass(generateAdapter = true)
data class CongregationData(
    val range: String?,
    val majorDimension: String?,
    val values: List< List<String> >?
)



