package com.altaie.domain.models.tiktok


import com.altaie.domain.utils.convertNumber
import com.altaie.domain.utils.convertToSize
import com.google.gson.annotations.SerializedName

data class TikTokPost(
    @SerializedName("aweme_detail")
    private val awesomeDetail: AwesomeDetail? = null,
    @SerializedName("status_code")
    val statusCode: Int? = null
) {
    data class AwesomeDetail(
        @SerializedName("author")
        val author: Author? = null,
        @SerializedName("aweme_id")
        val awesomeId: String? = null,
        @SerializedName("create_time")
        val createTime: Int? = null,
        @SerializedName("desc")
        val desc: String? = null,
        @SerializedName("music")
        val music: Music? = null,
        @SerializedName("prevent_download")
        val preventDownload: Boolean? = null,
        @SerializedName("region")
        val region: String? = null,
        @SerializedName("share_url")
        val shareUrl: String? = null,
        @SerializedName("statistics")
        val statistics: Statistics? = null,
        @SerializedName("video")
        val video: Video? = null
    )

    val socialName: String = "TikTok"
    val comments get() = awesomeDetail?.statistics?.commentCount?.convertNumber() ?: ""
    val downloads get() = awesomeDetail?.statistics?.downloadCount?.convertNumber() ?: ""
    val likes get() = awesomeDetail?.statistics?.diggCount?.convertNumber() ?: ""
    val nickname get() = awesomeDetail?.author?.nickname ?: ""
    val profileImageUrl get() = awesomeDetail?.author?.profileImageUrl ?: ""
    val saved get() = awesomeDetail?.statistics?.collectCount?.convertNumber() ?: ""
    val shares get() = awesomeDetail?.statistics?.shareCount?.convertNumber() ?: ""
    val username get() = awesomeDetail?.author?.username ?: ""
    val views get() = awesomeDetail?.statistics?.playCount?.convertNumber() ?: ""
    val videoUrl get() = awesomeDetail?.video?.url
    val audioUrl get() = awesomeDetail?.music?.url
    val videoSize get() = awesomeDetail?.video?.size?.convertToSize() ?: "0B"
    val coverUrl get() = awesomeDetail?.video?.coverUrl
}