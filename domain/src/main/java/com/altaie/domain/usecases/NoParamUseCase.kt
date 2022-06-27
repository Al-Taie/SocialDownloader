package com.altaie.domain.usecases

interface NoParamUseCase<out T> {
    suspend fun execute(): T
}