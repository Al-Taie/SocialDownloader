package com.altaie.domain.models

sealed class Resources<out T> {
    object Loading : Resources<Nothing>()
    object Init : Resources<Nothing>()
    data class Success<out T>(val data: T) : Resources<T>()
    data class Empty<T>(val data: T) : Resources<T>()
    data class Error(val message: String) : Resources<Nothing>()

    val toData get() : T? =  (this as? Success)?.data
    val isLoading get() = this is Loading
}
