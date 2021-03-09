package com.example.congregationinfo

import android.app.Application

public class Global : Application() {
    companion object {
        @JvmField
        var DataArray = emptyArray<String>()
    }
}