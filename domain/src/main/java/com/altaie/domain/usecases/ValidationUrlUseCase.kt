package com.altaie.domain.usecases

import com.altaie.domain.models.ValidationResult

object ValidationUrlUseCase {
    operator fun invoke(url: String): ValidationResult {
        val tiktokPostId = url.getTikTokId()
        return if (url.contains(TIKTOK) && !tiktokPostId.isNullOrEmpty())
            ValidationResult(isSuccessful = true, data = tiktokPostId)
        else
            ValidationResult(errorMessage = ERROR_MESSAGE)
    }

    private const val TIKTOK = "tiktok"
    private const val ERROR_MESSAGE = "Url Not Valid!"
    private fun Regex.search(string: String) = find(string)?.groups?.last()?.value
    private fun String.getTikTokId() = "video/(\\d+)".toRegex().search(this)
}
