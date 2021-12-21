package com.example.congregationinfo.util

import android.os.Message
import java.lang.Exception

sealed class NetworkResult<out T: Any>
class NetworkSuccess<K : Any>(val result: K):NetworkResult<K>()
class NetworkError(val errorMessage: Exception):NetworkResult<Nothing>()