package com.example.congregationinfo.data

import android.app.Application

class Global : Application() {
    companion object {
        var firstStartCounter=0
        var name = ""
        var HARDD_CODE="55555"
        var resultDate=""
        var resultValues: List<List<String>> = emptyList()
    }
}