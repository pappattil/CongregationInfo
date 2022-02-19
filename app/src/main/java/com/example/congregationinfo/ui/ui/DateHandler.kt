package com.example.congregationinfo.ui.ui

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

interface DateHandler {
   @SuppressLint("SimpleDateFormat")
   fun dateExam(value: String): Int {
        var dataDate = value
        dataDate = dataDate.replace(".", "")
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val currentDate = dateFormat.format(Date())
        return (currentDate.toInt() - dataDate.toInt())
    }

    fun firstSun (result: List<List<String>>): String{
        var firstSunday = result[0][1]
        val columnSize = (result.size).minus(1)
        var i = 0
        while (i < columnSize) {
            val rowSize = (result[i].size).minus(1)
            for (j in 0..rowSize) {
                if (result[i][j] == ";") {
                    firstSunday = result[i][j + 1]
                    i = columnSize
                }
            }
            i++
        }
        return firstSunday
    }
}