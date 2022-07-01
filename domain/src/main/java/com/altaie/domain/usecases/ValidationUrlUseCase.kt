package com.altaie.domain.usecases

import com.altaie.domain.models.ValidationResult
import com.altaie.domain.utils.Constant.ERROR_MESSAGE
import com.altaie.domain.utils.Constant.TIKTOK
import com.altaie.domain.utils.search

object ValidationUrlUseCase {
    operator fun invoke(url: String): ValidationResult {
        val tiktokPostId = url.getTikTokId()
        return if (url.contains(TIKTOK) && !tiktokPostId.isNullOrEmpty())
            ValidationResult(isSuccessful = true, data = tiktokPostId)
        else
            ValidationResult(errorMessage = ERROR_MESSAGE)
    }

    private fun String.getTikTokId() = "video/(\\d+)".toRegex().search(this)
}
