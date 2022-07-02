package com.altaie.domain.usecases

import com.altaie.domain.models.ValidationResult
import com.altaie.domain.repositories.TikTokRepository
import com.altaie.domain.utils.Constant.ERROR_MESSAGE_SHORT_LINK
import com.altaie.domain.utils.Constant.HTTP
import com.altaie.domain.utils.Constant.HTTPS
import com.altaie.domain.utils.search
import javax.inject.Inject


class GetIdFromUrlUseCase @Inject constructor(
    private val repository: TikTokRepository
) {

    suspend operator fun invoke(validationResult: ValidationResult): ValidationResult {
        val url = validationResult.data

        if (!validationResult.isShortLink)
            return ValidationResult(data = url.getTikTokId()!!, isSuccessful = true)

        val tiktokId = (if (url.startsWith(HTTP) || url.startsWith(HTTPS))
            repository.getRedirectUrl(url = url)
        else
            repository.getRedirectUrl(url = HTTPS + url))?.getTikTokId()

        return if (tiktokId == null)
            ValidationResult(errorMessage = ERROR_MESSAGE_SHORT_LINK)
        else
            ValidationResult(data = tiktokId, isSuccessful = true)

    }

    private fun String.getTikTokId() = "video/(\\d+)".toRegex().search(this)
}