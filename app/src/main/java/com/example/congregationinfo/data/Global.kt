package com.example.congregationinfo.data

import android.app.Application

class Global : Application() {
    companion object {
        var firstStartCounter=0
        var name = ""
        var counter=0
        var resultDate=""
        var resultValues: List<List<String>> = emptyList()
    }
}