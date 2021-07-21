package com.serhii.repository

sealed class Resource<out R> {

    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val Resource<*>.hasData
    get() = this is Resource.Success && data != null

val Resource<*>.isNullData
    get() = this is Resource.Success && data == null

val Resource<List<*>>.isEmptyList
    get() = this is Resource.Success && data?.isEmpty() ?: false
