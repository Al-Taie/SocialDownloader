package com.altaie.domain.usecases

import com.altaie.domain.models.ValidationResult
import com.altaie.domain.repositories.TikTokRepository
import com.altaie.domain.utils.Constant.ERROR_MESSAGE
import com.altaie.domain.utils.Constant.ERROR_MESSAGE_SHORT_LINK
import com.altaie.domain.utils.Constant.HTTP
import com.altaie.domain.utils.Constant.HTTPS
import javax.inject.Inject


class GetIdFromShortLinkUseCase @Inject constructor(private val repository: TikTokRepository) {
    suspend operator fun invoke(shortLink: String): ValidationResult {
        if (!shortLink.isTickTokUrl())
            return ValidationResult(errorMessage = ERROR_MESSAGE_SHORT_LINK)

        val url = if (shortLink.startsWith(HTTP) || shortLink.startsWith(HTTPS))
            repository.getRedirectUrl(url = shortLink)
        else
            repository.getRedirectUrl(url = HTTPS + shortLink)

        return ValidationUrlUseCase(url.toString())
    }

    private fun String.isTickTokUrl() = "vm\\.tiktok\\.com/(\\w+)".toRegex().containsMatchIn(this)
}