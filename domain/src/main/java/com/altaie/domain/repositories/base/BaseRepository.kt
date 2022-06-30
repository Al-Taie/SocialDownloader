package com.altaie.domain.repositories.base

import com.altaie.domain.models.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

interface BaseRepository {
    fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<Resources<T?>> =
        flow {
            emit(Resources.Loading)
            emit(checkIfSuccessful(function()))
        }.flowOn(Dispatchers.IO).catch {
            emit(Resources.Error(it.message.toString()))
        }

    private fun <T> checkIfSuccessful(result: Response<T>): Resources<T?> =
        try {
            if (result.isSuccessful) {
                Resources.Success(result.body())
            } else {
                Resources.Error(result.message())
            }
        } catch (e: Exception) {
            Resources.Error(e.message.toString())
        }
}