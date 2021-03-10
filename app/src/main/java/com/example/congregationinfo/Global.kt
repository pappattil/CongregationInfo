package com.example.congregationinfo

import android.app.Application

class Global : Application() {
    companion object {
        @JvmField
        var DataArray = emptyArray<String>()
        var Name = ""
    }
}