package com.altaie.domain.models

data class ValidationResult(
    val data: String = "",
    val isSuccessful: Boolean = false,
    val isShortLink: Boolean = false,
    val errorMessage: String? = null
)