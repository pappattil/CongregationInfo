package com.example.congregationinfo.data

import android.app.Application

class Global : Application() {
    companion object {
        @JvmField
        var DataArray = emptyArray<String>()
        var Name = ""
    }
}