package com.example.congregationinfo.ui.ui

import java.text.SimpleDateFormat
import java.util.*

interface DateHandler {

   fun dateExam(value: String): Int {
        var dataDate = value
        dataDate = dataDate.replace(".", "")
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        return (currentDate.toInt() - dataDate.toInt())
    }

    fun firstSun (result: List<List<String>>): String{
       // System.out.println("ERRSTD:"+result[0][1])
        var firstSunday = result[0][1]
        val columnSize = (result.size).minus(1)
        var i = 1
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