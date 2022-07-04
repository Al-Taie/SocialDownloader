package com.altaie.domain.repositories.base

import com.altaie.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

interface BaseRepository {
    fun <T, R> wrapWithFlow(
        apiCall: suspend () -> Response<T>,
        mapper: (T?) -> R?
    ): Flow<Resource<R?>> =
        flow {
            emit(Resource.Loading)
            emit(checkIfSuccessful(result = apiCall(), mapper = mapper))
        }.flowOn(Dispatchers.IO).catch {
            emit(Resource.Error(it.message.toString()))
        }

    private fun <T, R> checkIfSuccessful(result: Response<T>, mapper: (T?) -> R?): Resource<R?> =
        try {
            if (result.isSuccessful) {
                Resource.Success(mapper(result.body()))
            } else {
                Resource.Error(result.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
}