package com.altaie.domain.usecases

import com.altaie.domain.models.ValidationResult
import com.altaie.domain.utils.Constant.ERROR_MESSAGE

object ValidationUrlUseCase {

    operator fun invoke(url: String): ValidationResult {
        return if (url.isTickTokUrl()) {
            ValidationResult(isSuccessful = true, data = url)
        } else if (url.isTickTokShortLink()) {
            ValidationResult(isSuccessful = true, isShortLink = true, data = url)
        } else {
            ValidationResult(errorMessage = ERROR_MESSAGE)
        }
    }

    private fun String.isTickTokUrl() =
        "tiktok\\.com/@\\w+/video/\\d+".toRegex().containsMatchIn(this)

    private fun String.isTickTokShortLink() =
        "vm\\.tiktok\\.com/\\w+".toRegex().containsMatchIn(this)

}
