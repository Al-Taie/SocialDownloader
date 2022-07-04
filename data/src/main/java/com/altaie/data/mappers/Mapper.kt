package com.altaie.data.mappers

import com.altaie.data.models.tiktok.TikTokPostDto
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.domain.utils.convertNumber
import com.altaie.domain.utils.convertToSize
import retrofit2.Response

fun TikTokPostDto.toModel(): TikTokPost {
    if (awesomeDetail == null) {
        return TikTokPost()
    }
    return with(awesomeDetail) {
        TikTokPost(
            audioUrl = music?.url ?: "",
            videoUrl = video?.url ?: "",
            coverUrl = video?.coverUrl ?: "",
            nickname = author?.nickname ?: "",
            username = author?.username ?: "",
            profileImageUrl = author?.profileImageUrl ?: "",
            videoSize = video?.size?.convertToSize() ?: "0B",
            views = statistics?.playCount.convertNumber(),
            likes = statistics?.diggCount.convertNumber(),
            shares = statistics?.shareCount.convertNumber(),
            saved = statistics?.collectCount.convertNumber(),
            comments = statistics?.commentCount.convertNumber(),
            downloads = statistics?.downloadCount.convertNumber(),
        )
    }
}
