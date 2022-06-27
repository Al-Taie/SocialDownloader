package com.altaie.domain.usecases

interface ParamUseCase<out T> {
    suspend fun execute(vararg args: Any): T
}