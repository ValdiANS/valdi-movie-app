package com.myapplication.core.data

//sealed class Resource<out R> private constructor() {
//    data class Success<out T>(val data: T) : Resource<T>()
//    data class Error(val error: String) : Resource<Nothing>()
//    object Loading : Resource<Nothing>()
//}

sealed class Resource<T> private constructor(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}