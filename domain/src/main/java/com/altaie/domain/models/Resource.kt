package com.altaie.domain.models

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    object Init : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Empty<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()

    val toData get() : T? =  (this as? Success)?.data
    val isLoading get() = this is Loading
}
